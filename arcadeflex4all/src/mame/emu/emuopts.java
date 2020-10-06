package mame.emu;

public class emuopts {
/*TODO*///// license:BSD-3-Clause
/*TODO*///// copyright-holders:Aaron Giles
/*TODO*////***************************************************************************
/*TODO*///
/*TODO*///    emuopts.cpp
/*TODO*///
/*TODO*///    Options file and command line management.
/*TODO*///
/*TODO*///***************************************************************************/
/*TODO*///
/*TODO*///#include "emu.h"
/*TODO*///#include "emuopts.h"
/*TODO*///#include "drivenum.h"
/*TODO*///#include "softlist_dev.h"
/*TODO*///#include "hashfile.h"
/*TODO*///
/*TODO*///#include <stack>
/*TODO*///
/*TODO*///
/*TODO*/////**************************************************************************
/*TODO*/////  CORE EMULATOR OPTIONS
/*TODO*/////**************************************************************************
/*TODO*///
/*TODO*///const options_entry emu_options::s_option_entries[] =
/*TODO*///{
/*TODO*///	// unadorned options - only a single one supported at the moment
/*TODO*///	{ OPTION_SYSTEMNAME,                                 nullptr,     OPTION_STRING,     nullptr },
/*TODO*///	{ OPTION_SOFTWARENAME,                               nullptr,     OPTION_STRING,     nullptr },
/*TODO*///
/*TODO*///	// config options
/*TODO*///	{ nullptr,                                           nullptr,     OPTION_HEADER,     "CORE CONFIGURATION OPTIONS" },
/*TODO*///	{ OPTION_READCONFIG ";rc",                           "1",         OPTION_BOOLEAN,    "enable loading of configuration files" },
/*TODO*///	{ OPTION_WRITECONFIG ";wc",                          "0",         OPTION_BOOLEAN,    "write configuration to (driver).ini on exit" },
/*TODO*///
/*TODO*///	// search path options
/*TODO*///	{ nullptr,                                           nullptr,     OPTION_HEADER,     "CORE SEARCH PATH OPTIONS" },
/*TODO*///	{ OPTION_HOMEPATH,                                   ".",         OPTION_STRING,     "path to base folder for plugin data (read/write)" },
/*TODO*///	{ OPTION_MEDIAPATH ";rp;biospath;bp",                "roms",      OPTION_STRING,     "path to ROM sets and hard disk images" },
/*TODO*///	{ OPTION_HASHPATH ";hash_directory;hash",            "hash",      OPTION_STRING,     "path to software definition files" },
/*TODO*///	{ OPTION_SAMPLEPATH ";sp",                           "samples",   OPTION_STRING,     "path to audio sample sets" },
/*TODO*///	{ OPTION_ARTPATH,                                    "artwork",   OPTION_STRING,     "path to artwork files" },
/*TODO*///	{ OPTION_CTRLRPATH,                                  "ctrlr",     OPTION_STRING,     "path to controller definitions" },
/*TODO*///	{ OPTION_INIPATH,                                    ".;ini;ini/presets",     OPTION_STRING,     "path to ini files" },
/*TODO*///	{ OPTION_FONTPATH,                                   ".",         OPTION_STRING,     "path to font files" },
/*TODO*///	{ OPTION_CHEATPATH,                                  "cheat",     OPTION_STRING,     "path to cheat files" },
/*TODO*///	{ OPTION_CROSSHAIRPATH,                              "crosshair", OPTION_STRING,     "path to crosshair files" },
/*TODO*///	{ OPTION_PLUGINSPATH,                                "plugins",   OPTION_STRING,     "path to plugin files" },
/*TODO*///	{ OPTION_LANGUAGEPATH,                               "language",  OPTION_STRING,     "path to UI translation files" },
/*TODO*///	{ OPTION_SWPATH,                                     "software",  OPTION_STRING,     "path to loose software" },
/*TODO*///
/*TODO*///	// output directory options
/*TODO*///	{ nullptr,                                           nullptr,     OPTION_HEADER,     "CORE OUTPUT DIRECTORY OPTIONS" },
/*TODO*///	{ OPTION_CFG_DIRECTORY,                              "cfg",       OPTION_STRING,     "directory to save configurations" },
/*TODO*///	{ OPTION_NVRAM_DIRECTORY,                            "nvram",     OPTION_STRING,     "directory to save NVRAM contents" },
/*TODO*///	{ OPTION_INPUT_DIRECTORY,                            "inp",       OPTION_STRING,     "directory to save input device logs" },
/*TODO*///	{ OPTION_STATE_DIRECTORY,                            "sta",       OPTION_STRING,     "directory to save states" },
/*TODO*///	{ OPTION_SNAPSHOT_DIRECTORY,                         "snap",      OPTION_STRING,     "directory to save/load screenshots" },
/*TODO*///	{ OPTION_DIFF_DIRECTORY,                             "diff",      OPTION_STRING,     "directory to save hard drive image difference files" },
/*TODO*///	{ OPTION_COMMENT_DIRECTORY,                          "comments",  OPTION_STRING,     "directory to save debugger comments" },
/*TODO*///
/*TODO*///	// state/playback options
/*TODO*///	{ nullptr,                                           nullptr,     OPTION_HEADER,     "CORE STATE/PLAYBACK OPTIONS" },
/*TODO*///	{ OPTION_STATE,                                      nullptr,     OPTION_STRING,     "saved state to load" },
/*TODO*///	{ OPTION_AUTOSAVE,                                   "0",         OPTION_BOOLEAN,    "automatically restore state on start and save on exit for supported systems" },
/*TODO*///	{ OPTION_REWIND,                                     "0",         OPTION_BOOLEAN,    "enable rewind savestates" },
/*TODO*///	{ OPTION_REWIND_CAPACITY "(1-2048)",                 "100",       OPTION_INTEGER,    "rewind buffer size in megabytes" },
/*TODO*///	{ OPTION_PLAYBACK ";pb",                             nullptr,     OPTION_STRING,     "playback an input file" },
/*TODO*///	{ OPTION_RECORD ";rec",                              nullptr,     OPTION_STRING,     "record an input file" },
/*TODO*///	{ OPTION_RECORD_TIMECODE,                            "0",         OPTION_BOOLEAN,    "record an input timecode file (requires -record option)" },
/*TODO*///	{ OPTION_EXIT_AFTER_PLAYBACK,                        "0",         OPTION_BOOLEAN,    "close the program at the end of playback" },
/*TODO*///
/*TODO*///	{ OPTION_MNGWRITE,                                   nullptr,     OPTION_STRING,     "optional filename to write a MNG movie of the current session" },
/*TODO*///	{ OPTION_AVIWRITE,                                   nullptr,     OPTION_STRING,     "optional filename to write an AVI movie of the current session" },
/*TODO*///	{ OPTION_WAVWRITE,                                   nullptr,     OPTION_STRING,     "optional filename to write a WAV file of the current session" },
/*TODO*///	{ OPTION_SNAPNAME,                                   "%g/%i",     OPTION_STRING,     "override of the default snapshot/movie naming; %g == gamename, %i == index" },
/*TODO*///	{ OPTION_SNAPSIZE,                                   "auto",      OPTION_STRING,     "specify snapshot/movie resolution (<width>x<height>) or 'auto' to use minimal size " },
/*TODO*///	{ OPTION_SNAPVIEW,                                   "internal",  OPTION_STRING,     "specify snapshot/movie view or 'internal' to use internal pixel-aspect views" },
/*TODO*///	{ OPTION_SNAPBILINEAR,                               "1",         OPTION_BOOLEAN,    "specify if the snapshot/movie should have bilinear filtering applied" },
/*TODO*///	{ OPTION_STATENAME,                                  "%g",        OPTION_STRING,     "override of the default state subfolder naming; %g == gamename" },
/*TODO*///	{ OPTION_BURNIN,                                     "0",         OPTION_BOOLEAN,    "create burn-in snapshots for each screen" },
/*TODO*///
/*TODO*///	// performance options
/*TODO*///	{ nullptr,                                           nullptr,     OPTION_HEADER,     "CORE PERFORMANCE OPTIONS" },
/*TODO*///	{ OPTION_AUTOFRAMESKIP ";afs",                       "0",         OPTION_BOOLEAN,    "enable automatic frameskip adjustment to maintain emulation speed" },
/*TODO*///	{ OPTION_FRAMESKIP ";fs(0-10)",                      "0",         OPTION_INTEGER,    "set frameskip to fixed value, 0-10 (upper limit with autoframeskip)" },
/*TODO*///	{ OPTION_SECONDS_TO_RUN ";str",                      "0",         OPTION_INTEGER,    "number of emulated seconds to run before automatically exiting" },
/*TODO*///	{ OPTION_THROTTLE,                                   "1",         OPTION_BOOLEAN,    "throttle emulation to keep system running in sync with real time" },
/*TODO*///	{ OPTION_SLEEP,                                      "1",         OPTION_BOOLEAN,    "enable sleeping, which gives time back to other applications when idle" },
/*TODO*///	{ OPTION_SPEED "(0.01-100)",                         "1.0",       OPTION_FLOAT,      "controls the speed of gameplay, relative to realtime; smaller numbers are slower" },
/*TODO*///	{ OPTION_REFRESHSPEED ";rs",                         "0",         OPTION_BOOLEAN,    "automatically adjust emulation speed to keep the emulated refresh rate slower than the host screen" },
/*TODO*///	{ OPTION_LOWLATENCY ";lolat",                        "0",         OPTION_BOOLEAN,    "draws new frame before throttling to reduce input latency" },
/*TODO*///
/*TODO*///	// render options
/*TODO*///	{ nullptr,                                           nullptr,     OPTION_HEADER,     "CORE RENDER OPTIONS" },
/*TODO*///	{ OPTION_KEEPASPECT ";ka",                           "1",         OPTION_BOOLEAN,    "maintain aspect ratio when scaling to fill output screen/window" },
/*TODO*///	{ OPTION_UNEVENSTRETCH ";ues",                       "1",         OPTION_BOOLEAN,    "allow non-integer ratios when scaling to fill output screen/window horizontally or vertically" },
/*TODO*///	{ OPTION_UNEVENSTRETCHX ";uesx",                     "0",         OPTION_BOOLEAN,    "allow non-integer ratios when scaling to fill output screen/window horizontally"},
/*TODO*///	{ OPTION_UNEVENSTRETCHY ";uesy",                     "0",         OPTION_BOOLEAN,    "allow non-integer ratios when scaling to fill otuput screen/window vertially"},
/*TODO*///	{ OPTION_AUTOSTRETCHXY ";asxy",                      "0",         OPTION_BOOLEAN,    "automatically apply -unevenstretchx/y based on source native orientation"},
/*TODO*///	{ OPTION_INTOVERSCAN ";ios",                         "0",         OPTION_BOOLEAN,    "allow overscan on integer scaled targets"},
/*TODO*///	{ OPTION_INTSCALEX ";sx",                            "0",         OPTION_INTEGER,    "set horizontal integer scale factor"},
/*TODO*///	{ OPTION_INTSCALEY ";sy",                            "0",         OPTION_INTEGER,    "set vertical integer scale factor"},
/*TODO*///
/*TODO*///	// rotation options
/*TODO*///	{ nullptr,                                           nullptr,     OPTION_HEADER,     "CORE ROTATION OPTIONS" },
/*TODO*///	{ OPTION_ROTATE,                                     "1",         OPTION_BOOLEAN,    "rotate the game screen according to the game's orientation when needed" },
/*TODO*///	{ OPTION_ROR,                                        "0",         OPTION_BOOLEAN,    "rotate screen clockwise 90 degrees" },
/*TODO*///	{ OPTION_ROL,                                        "0",         OPTION_BOOLEAN,    "rotate screen counterclockwise 90 degrees" },
/*TODO*///	{ OPTION_AUTOROR,                                    "0",         OPTION_BOOLEAN,    "automatically rotate screen clockwise 90 degrees if vertical" },
/*TODO*///	{ OPTION_AUTOROL,                                    "0",         OPTION_BOOLEAN,    "automatically rotate screen counterclockwise 90 degrees if vertical" },
/*TODO*///	{ OPTION_FLIPX,                                      "0",         OPTION_BOOLEAN,    "flip screen left-right" },
/*TODO*///	{ OPTION_FLIPY,                                      "0",         OPTION_BOOLEAN,    "flip screen upside-down" },
/*TODO*///
/*TODO*///	// artwork options
/*TODO*///	{ nullptr,                                           nullptr,     OPTION_HEADER,     "CORE ARTWORK OPTIONS" },
/*TODO*///	{ OPTION_ARTWORK_CROP ";artcrop",                    "0",         OPTION_BOOLEAN,    "crop artwork so emulated screen image fills output screen/window in one axis" },
/*TODO*///	{ OPTION_FALLBACK_ARTWORK,                           nullptr,     OPTION_STRING,     "fallback artwork if no external artwork or internal driver layout defined" },
/*TODO*///	{ OPTION_OVERRIDE_ARTWORK,                           nullptr,     OPTION_STRING,     "override artwork for external artwork and internal driver layout" },
/*TODO*///
/*TODO*///	// screen options
/*TODO*///	{ nullptr,                                           nullptr,     OPTION_HEADER,     "CORE SCREEN OPTIONS" },
/*TODO*///	{ OPTION_BRIGHTNESS "(0.1-2.0)",                     "1.0",       OPTION_FLOAT,      "default game screen brightness correction" },
/*TODO*///	{ OPTION_CONTRAST "(0.1-2.0)",                       "1.0",       OPTION_FLOAT,      "default game screen contrast correction" },
/*TODO*///	{ OPTION_GAMMA "(0.1-3.0)",                          "1.0",       OPTION_FLOAT,      "default game screen gamma correction" },
/*TODO*///	{ OPTION_PAUSE_BRIGHTNESS "(0.0-1.0)",               "0.65",      OPTION_FLOAT,      "amount to scale the screen brightness when paused" },
/*TODO*///	{ OPTION_EFFECT,                                     "none",      OPTION_STRING,     "name of a PNG file to use for visual effects, or 'none'" },
/*TODO*///
/*TODO*///	// vector options
/*TODO*///	{ nullptr,                                           nullptr,     OPTION_HEADER,     "CORE VECTOR OPTIONS" },
/*TODO*///	{ OPTION_BEAM_WIDTH_MIN,                             "1.0",       OPTION_FLOAT,      "set vector beam width minimum" },
/*TODO*///	{ OPTION_BEAM_WIDTH_MAX,                             "1.0",       OPTION_FLOAT,      "set vector beam width maximum" },
/*TODO*///	{ OPTION_BEAM_DOT_SIZE,                              "1.0",       OPTION_FLOAT,      "set vector beam size for dots" },
/*TODO*///	{ OPTION_BEAM_INTENSITY_WEIGHT,                      "0",         OPTION_FLOAT,      "set vector beam intensity weight " },
/*TODO*///	{ OPTION_FLICKER,                                    "0",         OPTION_FLOAT,      "set vector flicker effect" },
/*TODO*///
/*TODO*///	// sound options
/*TODO*///	{ nullptr,                                           nullptr,     OPTION_HEADER,     "CORE SOUND OPTIONS" },
/*TODO*///	{ OPTION_SAMPLERATE ";sr(1000-1000000)",             "48000",     OPTION_INTEGER,    "set sound output sample rate" },
/*TODO*///	{ OPTION_SAMPLES,                                    "1",         OPTION_BOOLEAN,    "enable the use of external samples if available" },
/*TODO*///	{ OPTION_VOLUME ";vol",                              "0",         OPTION_INTEGER,    "sound volume in decibels (-32 min, 0 max)" },
/*TODO*///	{ OPTION_SPEAKER_REPORT,                             "0",         OPTION_INTEGER,    "print report of speaker ouput maxima (0=none, or 1-4 for more detail)" },
/*TODO*///
/*TODO*///	// input options
/*TODO*///	{ nullptr,                                           nullptr,     OPTION_HEADER,     "CORE INPUT OPTIONS" },
/*TODO*///	{ OPTION_COIN_LOCKOUT ";coinlock",                   "1",         OPTION_BOOLEAN,    "ignore coin inputs if coin lockout output is active" },
/*TODO*///	{ OPTION_CTRLR,                                      nullptr,     OPTION_STRING,     "preconfigure for specified controller" },
/*TODO*///	{ OPTION_MOUSE,                                      "0",         OPTION_BOOLEAN,    "enable mouse input" },
/*TODO*///	{ OPTION_JOYSTICK ";joy",                            "1",         OPTION_BOOLEAN,    "enable joystick input" },
/*TODO*///	{ OPTION_LIGHTGUN ";gun",                            "0",         OPTION_BOOLEAN,    "enable lightgun input" },
/*TODO*///	{ OPTION_MULTIKEYBOARD ";multikey",                  "0",         OPTION_BOOLEAN,    "enable separate input from each keyboard device (if present)" },
/*TODO*///	{ OPTION_MULTIMOUSE,                                 "0",         OPTION_BOOLEAN,    "enable separate input from each mouse device (if present)" },
/*TODO*///	{ OPTION_STEADYKEY ";steady",                        "0",         OPTION_BOOLEAN,    "enable steadykey support" },
/*TODO*///	{ OPTION_UI_ACTIVE,                                  "0",         OPTION_BOOLEAN,    "enable user interface on top of emulated keyboard (if present)" },
/*TODO*///	{ OPTION_OFFSCREEN_RELOAD ";reload",                 "0",         OPTION_BOOLEAN,    "convert lightgun button 2 into offscreen reload" },
/*TODO*///	{ OPTION_JOYSTICK_MAP ";joymap",                     "auto",      OPTION_STRING,     "explicit joystick map, or auto to auto-select" },
/*TODO*///	{ OPTION_JOYSTICK_DEADZONE ";joy_deadzone;jdz(0.00-1)",      "0.3",       OPTION_FLOAT,      "center deadzone range for joystick where change is ignored (0.0 center, 1.0 end)" },
/*TODO*///	{ OPTION_JOYSTICK_SATURATION ";joy_saturation;jsat(0.00-1)", "0.85",      OPTION_FLOAT,      "end of axis saturation range for joystick where change is ignored (0.0 center, 1.0 end)" },
/*TODO*///	{ OPTION_NATURAL_KEYBOARD ";nat",                    "0",         OPTION_BOOLEAN,    "specifies whether to use a natural keyboard or not" },
/*TODO*///	{ OPTION_JOYSTICK_CONTRADICTORY ";joy_contradictory","0",         OPTION_BOOLEAN,    "enable contradictory direction digital joystick input at the same time" },
/*TODO*///	{ OPTION_COIN_IMPULSE,                               "0",         OPTION_INTEGER,    "set coin impulse time (n<0 disable impulse, n==0 obey driver, 0<n set time n)" },
/*TODO*///
/*TODO*///	// input autoenable options
/*TODO*///	{ nullptr,                                           nullptr,     OPTION_HEADER,     "CORE INPUT AUTOMATIC ENABLE OPTIONS" },
/*TODO*///	{ OPTION_PADDLE_DEVICE ";paddle",                    "keyboard",  OPTION_STRING,     "enable (none|keyboard|mouse|lightgun|joystick) if a paddle control is present" },
/*TODO*///	{ OPTION_ADSTICK_DEVICE ";adstick",                  "keyboard",  OPTION_STRING,     "enable (none|keyboard|mouse|lightgun|joystick) if an analog joystick control is present" },
/*TODO*///	{ OPTION_PEDAL_DEVICE ";pedal",                      "keyboard",  OPTION_STRING,     "enable (none|keyboard|mouse|lightgun|joystick) if a pedal control is present" },
/*TODO*///	{ OPTION_DIAL_DEVICE ";dial",                        "keyboard",  OPTION_STRING,     "enable (none|keyboard|mouse|lightgun|joystick) if a dial control is present" },
/*TODO*///	{ OPTION_TRACKBALL_DEVICE ";trackball",              "keyboard",  OPTION_STRING,     "enable (none|keyboard|mouse|lightgun|joystick) if a trackball control is present" },
/*TODO*///	{ OPTION_LIGHTGUN_DEVICE,                            "keyboard",  OPTION_STRING,     "enable (none|keyboard|mouse|lightgun|joystick) if a lightgun control is present" },
/*TODO*///	{ OPTION_POSITIONAL_DEVICE,                          "keyboard",  OPTION_STRING,     "enable (none|keyboard|mouse|lightgun|joystick) if a positional control is present" },
/*TODO*///	{ OPTION_MOUSE_DEVICE,                               "mouse",     OPTION_STRING,     "enable (none|keyboard|mouse|lightgun|joystick) if a mouse control is present" },
/*TODO*///
/*TODO*///	// debugging options
/*TODO*///	{ nullptr,                                           nullptr,     OPTION_HEADER,     "CORE DEBUGGING OPTIONS" },
/*TODO*///	{ OPTION_VERBOSE ";v",                               "0",         OPTION_BOOLEAN,    "display additional diagnostic information" },
/*TODO*///	{ OPTION_LOG,                                        "0",         OPTION_BOOLEAN,    "generate an error.log file" },
/*TODO*///	{ OPTION_OSLOG,                                      "0",         OPTION_BOOLEAN,    "output error.log data to system diagnostic output (debugger or standard error)" },
/*TODO*///	{ OPTION_DEBUG ";d",                                 "0",         OPTION_BOOLEAN,    "enable/disable debugger" },
/*TODO*///	{ OPTION_UPDATEINPAUSE,                              "0",         OPTION_BOOLEAN,    "keep calling video updates while in pause" },
/*TODO*///	{ OPTION_DEBUGSCRIPT,                                nullptr,     OPTION_STRING,     "script for debugger" },
/*TODO*///	{ OPTION_DEBUGLOG,                                   "0",         OPTION_BOOLEAN,    "write debug console output to debug.log" },
/*TODO*///
/*TODO*///	// comm options
/*TODO*///	{ nullptr,                                           nullptr,     OPTION_HEADER,     "CORE COMM OPTIONS" },
/*TODO*///	{ OPTION_COMM_LOCAL_HOST,                            "0.0.0.0",   OPTION_STRING,     "local address to bind to" },
/*TODO*///	{ OPTION_COMM_LOCAL_PORT,                            "15112",     OPTION_STRING,     "local port to bind to" },
/*TODO*///	{ OPTION_COMM_REMOTE_HOST,                           "127.0.0.1", OPTION_STRING,     "remote address to connect to" },
/*TODO*///	{ OPTION_COMM_REMOTE_PORT,                           "15112",     OPTION_STRING,     "remote port to connect to" },
/*TODO*///	{ OPTION_COMM_FRAME_SYNC,                            "0",         OPTION_BOOLEAN,    "sync frames" },
/*TODO*///
/*TODO*///	// misc options
/*TODO*///	{ nullptr,                                           nullptr,     OPTION_HEADER,     "CORE MISC OPTIONS" },
/*TODO*///	{ OPTION_DRC,                                        "1",         OPTION_BOOLEAN,    "enable DRC CPU core if available" },
/*TODO*///	{ OPTION_DRC_USE_C,                                  "0",         OPTION_BOOLEAN,    "force DRC to use C backend" },
/*TODO*///	{ OPTION_DRC_LOG_UML,                                "0",         OPTION_BOOLEAN,    "write DRC UML disassembly log" },
/*TODO*///	{ OPTION_DRC_LOG_NATIVE,                             "0",         OPTION_BOOLEAN,    "write DRC native disassembly log" },
/*TODO*///	{ OPTION_BIOS,                                       nullptr,     OPTION_STRING,     "select the system BIOS to use" },
/*TODO*///	{ OPTION_CHEAT ";c",                                 "0",         OPTION_BOOLEAN,    "enable cheat subsystem" },
/*TODO*///	{ OPTION_SKIP_GAMEINFO,                              "0",         OPTION_BOOLEAN,    "skip displaying the system information screen at startup" },
/*TODO*///	{ OPTION_UI_FONT,                                    "default",   OPTION_STRING,     "specify a font to use" },
/*TODO*///	{ OPTION_UI,                                         "cabinet",   OPTION_STRING,     "type of UI (simple|cabinet)" },
/*TODO*///	{ OPTION_RAMSIZE ";ram",                             nullptr,     OPTION_STRING,     "size of RAM (if supported by driver)" },
/*TODO*///	{ OPTION_CONFIRM_QUIT,                               "0",         OPTION_BOOLEAN,    "ask for confirmation before exiting" },
/*TODO*///	{ OPTION_UI_MOUSE,                                   "1",         OPTION_BOOLEAN,    "display UI mouse cursor" },
/*TODO*///	{ OPTION_LANGUAGE ";lang",                           "English",   OPTION_STRING,     "set UI display language" },
/*TODO*///	{ OPTION_NVRAM_SAVE ";nvwrite",                      "1",         OPTION_BOOLEAN,    "save NVRAM data on exit" },
/*TODO*///
/*TODO*///	{ nullptr,                                           nullptr,     OPTION_HEADER,     "SCRIPTING OPTIONS" },
/*TODO*///	{ OPTION_AUTOBOOT_COMMAND ";ab",                     nullptr,     OPTION_STRING,     "command to execute after machine boot" },
/*TODO*///	{ OPTION_AUTOBOOT_DELAY,                             "0",         OPTION_INTEGER,    "delay before executing autoboot command (seconds)" },
/*TODO*///	{ OPTION_AUTOBOOT_SCRIPT ";script",                  nullptr,     OPTION_STRING,     "Lua script to execute after machine boot" },
/*TODO*///	{ OPTION_CONSOLE,                                    "0",         OPTION_BOOLEAN,    "enable emulator Lua console" },
/*TODO*///	{ OPTION_PLUGINS,                                    "1",         OPTION_BOOLEAN,    "enable Lua plugin support" },
/*TODO*///	{ OPTION_PLUGIN,                                     nullptr,     OPTION_STRING,     "list of plugins to enable" },
/*TODO*///	{ OPTION_NO_PLUGIN,                                  nullptr,     OPTION_STRING,     "list of plugins to disable" },
/*TODO*///
/*TODO*///	{ nullptr,                                           nullptr,     OPTION_HEADER,     "HTTP SERVER OPTIONS" },
/*TODO*///	{ OPTION_HTTP,                                       "0",         OPTION_BOOLEAN,    "enable HTTP server" },
/*TODO*///	{ OPTION_HTTP_PORT,                                  "8080",      OPTION_INTEGER,    "HTTP server port" },
/*TODO*///	{ OPTION_HTTP_ROOT,                                  "web",       OPTION_STRING,     "HTTP server document root" },
/*TODO*///
/*TODO*///	{ nullptr }
/*TODO*///};
/*TODO*///
/*TODO*///
/*TODO*///
/*TODO*/////**************************************************************************
/*TODO*/////  CUSTOM OPTION ENTRIES AND SUPPORT CLASSES
/*TODO*/////**************************************************************************
/*TODO*///
/*TODO*///namespace
/*TODO*///{
/*TODO*///	// custom option entry for the system name
/*TODO*///	class system_name_option_entry : public core_options::entry
/*TODO*///	{
/*TODO*///	public:
/*TODO*///		system_name_option_entry(emu_options &host)
/*TODO*///			: entry(OPTION_SYSTEMNAME)
/*TODO*///			, m_host(host)
/*TODO*///		{
/*TODO*///		}
/*TODO*///
/*TODO*///		virtual const char *value() const noexcept override
/*TODO*///		{
/*TODO*///			// This is returning an empty string instead of nullptr to signify that
/*TODO*///			// specifying the value is a meaningful operation.  The option types that
/*TODO*///			// return nullptr are option types that cannot have a value (e.g. - commands)
/*TODO*///			//
/*TODO*///			// See comments in core_options::entry::value() and core_options::simple_entry::value()
/*TODO*///			return m_host.system() ? m_host.system()->name : "";
/*TODO*///		}
/*TODO*///
/*TODO*///	protected:
/*TODO*///		virtual void internal_set_value(std::string &&newvalue) override
/*TODO*///		{
/*TODO*///			m_host.set_system_name(std::move(newvalue));
/*TODO*///		}
/*TODO*///
/*TODO*///	private:
/*TODO*///		emu_options &m_host;
/*TODO*///	};
/*TODO*///
/*TODO*///	// custom option entry for the software name
/*TODO*///	class software_name_option_entry : public core_options::entry
/*TODO*///	{
/*TODO*///	public:
/*TODO*///		software_name_option_entry(emu_options &host)
/*TODO*///			: entry(OPTION_SOFTWARENAME)
/*TODO*///			, m_host(host)
/*TODO*///		{
/*TODO*///		}
/*TODO*///
/*TODO*///	protected:
/*TODO*///		virtual void internal_set_value(std::string &&newvalue) override
/*TODO*///		{
/*TODO*///			m_host.set_software(std::move(newvalue));
/*TODO*///		}
/*TODO*///
/*TODO*///	private:
/*TODO*///		emu_options &m_host;
/*TODO*///	};
/*TODO*///
/*TODO*///	// custom option entry for slots
/*TODO*///	class slot_option_entry : public core_options::entry
/*TODO*///	{
/*TODO*///	public:
/*TODO*///		slot_option_entry(const char *name, slot_option &host)
/*TODO*///			: entry(name)
/*TODO*///			, m_host(host)
/*TODO*///		{
/*TODO*///		}
/*TODO*///
/*TODO*///		virtual const char *value() const noexcept override
/*TODO*///		{
/*TODO*///			const char *result = nullptr;
/*TODO*///			if (m_host.specified())
/*TODO*///			{
/*TODO*///				// m_temp is a temporary variable used to keep the specified value
/*TODO*///				// so the result can be returned as 'const char *'.  Obviously, this
/*TODO*///				// value will be trampled upon if value() is called again.  This doesn't
/*TODO*///				// happen in practice
/*TODO*///				//
/*TODO*///				// In reality, I want to really return std::optional<std::string> here
/*TODO*///				// FIXME: the std::string assignment can throw exceptions, and returning std::optional<std::string> also isn't safe in noexcept
/*TODO*///				m_temp = m_host.specified_value();
/*TODO*///				result = m_temp.c_str();
/*TODO*///			}
/*TODO*///			return result;
/*TODO*///		}
/*TODO*///
/*TODO*///	protected:
/*TODO*///		virtual void internal_set_value(std::string &&newvalue) override
/*TODO*///		{
/*TODO*///			m_host.specify(std::move(newvalue), false);
/*TODO*///		}
/*TODO*///
/*TODO*///	private:
/*TODO*///		slot_option &       m_host;
/*TODO*///		mutable std::string m_temp;
/*TODO*///	};
/*TODO*///
/*TODO*///	// custom option entry for images
/*TODO*///	class image_option_entry : public core_options::entry
/*TODO*///	{
/*TODO*///	public:
/*TODO*///		image_option_entry(std::vector<std::string> &&names, image_option &host)
/*TODO*///			: entry(std::move(names))
/*TODO*///			, m_host(host)
/*TODO*///		{
/*TODO*///		}
/*TODO*///
/*TODO*///		virtual const char *value() const noexcept override
/*TODO*///		{
/*TODO*///			return m_host.value().c_str();
/*TODO*///		}
/*TODO*///
/*TODO*///	protected:
/*TODO*///		virtual void internal_set_value(std::string &&newvalue) override
/*TODO*///		{
/*TODO*///			m_host.specify(std::move(newvalue), false);
/*TODO*///		}
/*TODO*///
/*TODO*///	private:
/*TODO*///		image_option &m_host;
/*TODO*///	};
/*TODO*///
/*TODO*///	// existing option tracker class; used by slot/image calculus to identify existing
/*TODO*///	// options for later purging
/*TODO*///	template<typename T>
/*TODO*///	class existing_option_tracker
/*TODO*///	{
/*TODO*///	public:
/*TODO*///		existing_option_tracker(const std::unordered_map<std::string, T> &map)
/*TODO*///		{
/*TODO*///			m_vec.reserve(map.size());
/*TODO*///			for (const auto &entry : map)
/*TODO*///				m_vec.push_back(&entry.first);
/*TODO*///		}
/*TODO*///
/*TODO*///		template<typename TStr>
/*TODO*///		void remove(const TStr &str)
/*TODO*///		{
/*TODO*///			auto iter = std::find_if(
/*TODO*///				m_vec.begin(),
/*TODO*///				m_vec.end(),
/*TODO*///				[&str](const auto &x) { return *x == str; });
/*TODO*///			if (iter != m_vec.end())
/*TODO*///				m_vec.erase(iter);
/*TODO*///		}
/*TODO*///
/*TODO*///		std::vector<const std::string *>::iterator begin() { return m_vec.begin(); }
/*TODO*///		std::vector<const std::string *>::iterator end() { return m_vec.end(); }
/*TODO*///
/*TODO*///	private:
/*TODO*///		std::vector<const std::string *> m_vec;
/*TODO*///	};
/*TODO*///
/*TODO*///
/*TODO*///	//-------------------------------------------------
/*TODO*///	//  get_full_option_names
/*TODO*///	//-------------------------------------------------
/*TODO*///
/*TODO*///	std::vector<std::string> get_full_option_names(const device_image_interface &image)
/*TODO*///	{
/*TODO*///		std::vector<std::string> result;
/*TODO*///		bool same_name = image.instance_name() == image.brief_instance_name();
/*TODO*///
/*TODO*///		result.push_back(image.instance_name());
/*TODO*///		if (!same_name)
/*TODO*///			result.push_back(image.brief_instance_name());
/*TODO*///
/*TODO*///		if (image.instance_name() != image.canonical_instance_name())
/*TODO*///		{
/*TODO*///			result.push_back(image.canonical_instance_name());
/*TODO*///			if (!same_name)
/*TODO*///				result.push_back(image.brief_instance_name() + "1");
/*TODO*///		}
/*TODO*///		return result;
/*TODO*///	}
/*TODO*///
/*TODO*///
/*TODO*///	//-------------------------------------------------
/*TODO*///	//  conditionally_peg_priority
/*TODO*///	//-------------------------------------------------
/*TODO*///
/*TODO*///	void conditionally_peg_priority(core_options::entry::weak_ptr &entry, bool peg_priority)
/*TODO*///	{
/*TODO*///		// if the [image|slot] entry was specified outside of the context of the options sytem, we need
/*TODO*///		// to peg the priority of any associated core_options::entry at the maximum priority
/*TODO*///		if (peg_priority && !entry.expired())
/*TODO*///			entry.lock()->set_priority(OPTION_PRIORITY_MAXIMUM);
/*TODO*///	}
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////**************************************************************************
/*TODO*/////  EMU OPTIONS
/*TODO*/////**************************************************************************
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  emu_options - constructor
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///emu_options::emu_options(option_support support)
/*TODO*///	: m_support(support)
/*TODO*///	, m_system(nullptr)
/*TODO*///	, m_coin_impulse(0)
/*TODO*///	, m_joystick_contradictory(false)
/*TODO*///	, m_sleep(true)
/*TODO*///	, m_refresh_speed(false)
/*TODO*///	, m_ui(UI_CABINET)
/*TODO*///{
/*TODO*///	// add entries
/*TODO*///	if (support == option_support::FULL || support == option_support::GENERAL_AND_SYSTEM)
/*TODO*///		add_entry(std::make_shared<system_name_option_entry>(*this));
/*TODO*///	if (support == option_support::FULL)
/*TODO*///		add_entry(std::make_shared<software_name_option_entry>(*this));
/*TODO*///	add_entries(emu_options::s_option_entries);
/*TODO*///
/*TODO*///	// adding handlers to keep copies of frequently requested options in member variables
/*TODO*///	set_value_changed_handler(OPTION_COIN_IMPULSE,              [this](const char *value) { m_coin_impulse = int_value(OPTION_COIN_IMPULSE); });
/*TODO*///	set_value_changed_handler(OPTION_JOYSTICK_CONTRADICTORY,    [this](const char *value) { m_joystick_contradictory = bool_value(OPTION_JOYSTICK_CONTRADICTORY); });
/*TODO*///	set_value_changed_handler(OPTION_SLEEP,                     [this](const char *value) { m_sleep = bool_value(OPTION_SLEEP); });
/*TODO*///	set_value_changed_handler(OPTION_REFRESHSPEED,              [this](const char *value) { m_refresh_speed = bool_value(OPTION_REFRESHSPEED); });
/*TODO*///	set_value_changed_handler(OPTION_UI, [this](const std::string &value)
/*TODO*///	{
/*TODO*///		if (value == "simple")
/*TODO*///			m_ui = UI_SIMPLE;
/*TODO*///		else
/*TODO*///			m_ui = UI_CABINET;
/*TODO*///	});
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  emu_options - destructor
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///emu_options::~emu_options()
/*TODO*///{
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  system_name
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///const char *emu_options::system_name() const
/*TODO*///{
/*TODO*///	return m_system ? m_system->name : "";
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  set_system_name - called to set the system
/*TODO*/////  name; will adjust slot/image options as appropriate
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///void emu_options::set_system_name(std::string &&new_system_name)
/*TODO*///{
/*TODO*///	const game_driver *new_system = nullptr;
/*TODO*///
/*TODO*///	// we are making an attempt - record what we're attempting
/*TODO*///	m_attempted_system_name = std::move(new_system_name);
/*TODO*///
/*TODO*///	// was a system name specified?
/*TODO*///	if (!m_attempted_system_name.empty())
/*TODO*///	{
/*TODO*///		// if so, first extract the base name (the reason for this is drag-and-drop on Windows; a side
/*TODO*///		// effect is a command line like 'mame pacman.foo' will work correctly, but so be it)
/*TODO*///		std::string new_system_base_name = core_filename_extract_base(m_attempted_system_name, true);
/*TODO*///
/*TODO*///		// perform the lookup (and error if it cannot be found)
/*TODO*///		int index = driver_list::find(new_system_base_name.c_str());
/*TODO*///		if (index < 0)
/*TODO*///			throw options_error_exception("Unknown system '%s'", m_attempted_system_name);
/*TODO*///		new_system = &driver_list::driver(index);
/*TODO*///	}
/*TODO*///
/*TODO*///	// did we change anything?
/*TODO*///	if (new_system != m_system)
/*TODO*///	{
/*TODO*///		// if so, specify the new system and update (if we're fully supporting slot/image options)
/*TODO*///		m_system = new_system;
/*TODO*///		m_software_name.clear();
/*TODO*///		if (m_support == option_support::FULL)
/*TODO*///			update_slot_and_image_options();
/*TODO*///	}
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  set_system_name - called to set the system
/*TODO*/////  name; will adjust slot/image options as appropriate
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///void emu_options::set_system_name(const std::string &new_system_name)
/*TODO*///{
/*TODO*///	set_system_name(std::string(new_system_name));
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  update_slot_and_image_options
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///void emu_options::update_slot_and_image_options()
/*TODO*///{
/*TODO*///	bool changed;
/*TODO*///	do
/*TODO*///	{
/*TODO*///		changed = false;
/*TODO*///
/*TODO*///		// first we add and remove slot options depending on what has been configured in the
/*TODO*///		// device, bringing m_slot_options up to a state where it matches machine_config
/*TODO*///		if (add_and_remove_slot_options())
/*TODO*///			changed = true;
/*TODO*///
/*TODO*///		// second, we perform an analgous operation with m_image_options
/*TODO*///		if (add_and_remove_image_options())
/*TODO*///			changed = true;
/*TODO*///
/*TODO*///		// if we changed anything, we should reevaluate existing options
/*TODO*///		if (changed)
/*TODO*///			reevaluate_default_card_software();
/*TODO*///	} while (changed);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  add_and_remove_slot_options - add any missing
/*TODO*/////  and/or purge extraneous slot options
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///bool emu_options::add_and_remove_slot_options()
/*TODO*///{
/*TODO*///	bool changed = false;
/*TODO*///
/*TODO*///	// first, create a list of existing slot options; this is so we can purge
/*TODO*///	// any stray slot options that are no longer pertinent when we're done
/*TODO*///	existing_option_tracker<::slot_option> existing(m_slot_options);
/*TODO*///
/*TODO*///	// it is perfectly legal for this to be called without a system; we
/*TODO*///	// need to check for that condition!
/*TODO*///	if (m_system)
/*TODO*///	{
/*TODO*///		// create the configuration
/*TODO*///		machine_config config(*m_system, *this);
/*TODO*///
/*TODO*///		for (const device_slot_interface &slot : slot_interface_iterator(config.root_device()))
/*TODO*///		{
/*TODO*///			// come up with the canonical name of the slot
/*TODO*///			const char *slot_option_name = slot.slot_name();
/*TODO*///
/*TODO*///			// erase this option from existing (so we don't purge it later)
/*TODO*///			existing.remove(slot_option_name);
/*TODO*///
/*TODO*///			// do we need to add this option?
/*TODO*///			if (!has_slot_option(slot_option_name))
/*TODO*///			{
/*TODO*///				// we do - add it to m_slot_options
/*TODO*///				auto pair = std::make_pair(slot_option_name, ::slot_option(*this, slot.default_option()));
/*TODO*///				::slot_option &new_option(m_slot_options.emplace(std::move(pair)).first->second);
/*TODO*///				changed = true;
/*TODO*///
/*TODO*///				// for non-fixed slots, this slot needs representation in the options collection
/*TODO*///				if (!slot.fixed())
/*TODO*///				{
/*TODO*///					// first device? add the header as to be pretty
/*TODO*///					const char *header = "SLOT DEVICES";
/*TODO*///					if (!header_exists(header))
/*TODO*///						add_header(header);
/*TODO*///
/*TODO*///					// create a new entry in the options
/*TODO*///					auto new_entry = new_option.setup_option_entry(slot_option_name);
/*TODO*///
/*TODO*///					// and add it
/*TODO*///					add_entry(std::move(new_entry), header);
/*TODO*///				}
/*TODO*///			}
/*TODO*///
/*TODO*///		}
/*TODO*///	}
/*TODO*///
/*TODO*///	// at this point we need to purge stray slot options that may no longer be pertinent
/*TODO*///	for (auto &opt_name : existing)
/*TODO*///	{
/*TODO*///		auto iter = m_slot_options.find(*opt_name);
/*TODO*///		assert(iter != m_slot_options.end());
/*TODO*///
/*TODO*///		// if this is represented in core_options, remove it
/*TODO*///		if (iter->second.option_entry())
/*TODO*///			remove_entry(*iter->second.option_entry());
/*TODO*///
/*TODO*///		// remove this option
/*TODO*///		m_slot_options.erase(iter);
/*TODO*///		changed = true;
/*TODO*///	}
/*TODO*///
/*TODO*///	return changed;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  add_and_remove_slot_options - add any missing
/*TODO*/////  and/or purge extraneous slot options
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///bool emu_options::add_and_remove_image_options()
/*TODO*///{
/*TODO*///	// The logic for image options is superficially similar to the logic for slot options, but
/*TODO*///	// there is one larger piece of complexity.  The image instance names (returned by the
/*TODO*///	// image_instance() call and surfaced in the UI) may change simply because we've added more
/*TODO*///	// devices.  This is because the instance_name() for a singular cartridge device might be
/*TODO*///	// "cartridge" starting out, but become "cartridge1" when another cartridge device is added.
/*TODO*///	//
/*TODO*///	// To get around this behavior, our internal data structures work in terms of what is
/*TODO*///	// returned by canonical_instance_name(), which will be something like "cartridge1" both
/*TODO*///	// for a singular cartridge device and the first cartridge in a multi cartridge system.
/*TODO*///	//
/*TODO*///	// The need for this behavior was identified by Tafoid when the following command line
/*TODO*///	// regressed:
/*TODO*///	//
/*TODO*///	//      mame snes bsxsore -cart2 bszelda
/*TODO*///	//
/*TODO*///	// Before we were accounting for this behavior, 'bsxsore' got stored in "cartridge" and
/*TODO*///	// the association got lost when the second cartridge was added.
/*TODO*///
/*TODO*///	bool changed = false;
/*TODO*///
/*TODO*///	// first, create a list of existing image options; this is so we can purge
/*TODO*///	// any stray slot options that are no longer pertinent when we're done; we
/*TODO*///	// have to do this for both "flavors" of name
/*TODO*///	existing_option_tracker<::image_option> existing(m_image_options_canonical);
/*TODO*///
/*TODO*///	// wipe the non-canonical image options; we're going to rebuild it
/*TODO*///	m_image_options.clear();
/*TODO*///
/*TODO*///	// it is perfectly legal for this to be called without a system; we
/*TODO*///	// need to check for that condition!
/*TODO*///	if (m_system)
/*TODO*///	{
/*TODO*///		// create the configuration
/*TODO*///		machine_config config(*m_system, *this);
/*TODO*///
/*TODO*///		// iterate through all image devices
/*TODO*///		for (device_image_interface &image : image_interface_iterator(config.root_device()))
/*TODO*///		{
/*TODO*///			const std::string &canonical_name(image.canonical_instance_name());
/*TODO*///
/*TODO*///			// erase this option from existing (so we don't purge it later)
/*TODO*///			existing.remove(canonical_name);
/*TODO*///
/*TODO*///			// do we need to add this option?
/*TODO*///			auto iter = m_image_options_canonical.find(canonical_name);
/*TODO*///			::image_option *this_option = iter != m_image_options_canonical.end() ? &iter->second : nullptr;
/*TODO*///			if (!this_option)
/*TODO*///			{
/*TODO*///				// we do - add it to both m_image_options_canonical and m_image_options
/*TODO*///				auto pair = std::make_pair(canonical_name, ::image_option(*this, image.canonical_instance_name()));
/*TODO*///				this_option = &m_image_options_canonical.emplace(std::move(pair)).first->second;
/*TODO*///				changed = true;
/*TODO*///
/*TODO*///				// if this image is user loadable, we have to surface it in the core_options
/*TODO*///				if (image.user_loadable())
/*TODO*///				{
/*TODO*///					// first device? add the header as to be pretty
/*TODO*///					const char *header = "IMAGE DEVICES";
/*TODO*///					if (!header_exists(header))
/*TODO*///						add_header(header);
/*TODO*///
/*TODO*///					// name this options
/*TODO*///					auto names = get_full_option_names(image);
/*TODO*///
/*TODO*///					// create a new entry in the options
/*TODO*///					auto new_entry = this_option->setup_option_entry(std::move(names));
/*TODO*///
/*TODO*///					// and add it
/*TODO*///					add_entry(std::move(new_entry), header);
/*TODO*///				}
/*TODO*///			}
/*TODO*///
/*TODO*///			// whether we added it or we didn't, we have to add it to the m_image_option map
/*TODO*///			m_image_options[image.instance_name()] = this_option;
/*TODO*///		}
/*TODO*///	}
/*TODO*///
/*TODO*///	// at this point we need to purge stray image options that may no longer be pertinent
/*TODO*///	for (auto &opt_name : existing)
/*TODO*///	{
/*TODO*///		auto iter = m_image_options_canonical.find(*opt_name);
/*TODO*///		assert(iter != m_image_options_canonical.end());
/*TODO*///
/*TODO*///		// if this is represented in core_options, remove it
/*TODO*///		if (iter->second.option_entry())
/*TODO*///			remove_entry(*iter->second.option_entry());
/*TODO*///
/*TODO*///		// remove this option
/*TODO*///		m_image_options_canonical.erase(iter);
/*TODO*///		changed = true;
/*TODO*///	}
/*TODO*///
/*TODO*///	return changed;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  reevaluate_default_card_software - based on recent
/*TODO*/////  changes in what images are mounted, give drivers
/*TODO*/////  a chance to specify new default slot options
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///void emu_options::reevaluate_default_card_software()
/*TODO*///{
/*TODO*///	// if we don't have a system specified, this is
/*TODO*///	// a meaningless operation
/*TODO*///	if (!m_system)
/*TODO*///		return;
/*TODO*///
/*TODO*///	bool found;
/*TODO*///	do
/*TODO*///	{
/*TODO*///		// set up the machine_config
/*TODO*///		machine_config config(*m_system, *this);
/*TODO*///		found = false;
/*TODO*///
/*TODO*///		// iterate through all slot devices
/*TODO*///		for (device_slot_interface &slot : slot_interface_iterator(config.root_device()))
/*TODO*///		{
/*TODO*///			// retrieve info about the device instance
/*TODO*///			auto &slot_opt(slot_option(slot.slot_name()));
/*TODO*///
/*TODO*///			// device_slot_interface::get_default_card_software() allows a device that
/*TODO*///			// implements both device_slot_interface and device_image_interface to
/*TODO*///			// probe an image and specify the card device that should be loaded
/*TODO*///			//
/*TODO*///			// In the repeated cycle of adding slots and slot devices, this gives a chance
/*TODO*///			// for devices to "plug in" default software list items.  Of course, the fact
/*TODO*///			// that this is all shuffling options is brittle and roundabout, but such is
/*TODO*///			// the nature of software lists.
/*TODO*///			//
/*TODO*///			// In reality, having some sort of hook into the pipeline of slot/device evaluation
/*TODO*///			// makes sense, but the fact that it is joined at the hip to device_image_interface
/*TODO*///			// and device_slot_interface is unfortunate
/*TODO*///			std::string default_card_software = get_default_card_software(slot);
/*TODO*///			if (slot_opt.default_card_software() != default_card_software)
/*TODO*///			{
/*TODO*///				slot_opt.set_default_card_software(std::move(default_card_software));
/*TODO*///
/*TODO*///				// calling set_default_card_software() can cause a cascade of slot/image
/*TODO*///				// evaluations; we need to bail out of this loop because the iterator
/*TODO*///				// may be bad
/*TODO*///				found = true;
/*TODO*///				break;
/*TODO*///			}
/*TODO*///		}
/*TODO*///	} while (found);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  get_default_card_software
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///std::string emu_options::get_default_card_software(device_slot_interface &slot)
/*TODO*///{
/*TODO*///	std::string image_path;
/*TODO*///	std::function<bool(util::core_file &, std::string&)> get_hashfile_extrainfo;
/*TODO*///
/*TODO*///	// figure out if an image option has been specified, and if so, get the image path out of the options
/*TODO*///	device_image_interface *image = dynamic_cast<device_image_interface *>(&slot);
/*TODO*///	if (image)
/*TODO*///	{
/*TODO*///		image_path = image_option(image->instance_name()).value();
/*TODO*///
/*TODO*///		get_hashfile_extrainfo = [image, this](util::core_file &file, std::string &extrainfo)
/*TODO*///		{
/*TODO*///			util::hash_collection hashes = image->calculate_hash_on_file(file);
/*TODO*///
/*TODO*///			return hashfile_extrainfo(
/*TODO*///					hash_path(),
/*TODO*///					image->device().mconfig().gamedrv(),
/*TODO*///					hashes,
/*TODO*///					extrainfo);
/*TODO*///		};
/*TODO*///	}
/*TODO*///
/*TODO*///	// create the hook
/*TODO*///	get_default_card_software_hook hook(image_path, std::move(get_hashfile_extrainfo));
/*TODO*///
/*TODO*///	// and invoke the slot's implementation of get_default_card_software()
/*TODO*///	return slot.get_default_card_software(hook);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  set_software - called to load "unqualified"
/*TODO*/////  software out of a software list (e.g. - "mame nes 'zelda'")
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///void emu_options::set_software(std::string &&new_software)
/*TODO*///{
/*TODO*///	// identify any options as a result of softlists
/*TODO*///	software_options softlist_opts = evaluate_initial_softlist_options(new_software);
/*TODO*///
/*TODO*///	while (!softlist_opts.slot.empty() || !softlist_opts.image.empty())
/*TODO*///	{
/*TODO*///		// track how many options we have
/*TODO*///		size_t before_size = softlist_opts.slot.size() + softlist_opts.image.size();
/*TODO*///
/*TODO*///		// keep a list of deferred options, in case anything is applied
/*TODO*///		// out of order
/*TODO*///		software_options deferred_opts;
/*TODO*///
/*TODO*///		// distribute slot options
/*TODO*///		for (auto &slot_opt : softlist_opts.slot)
/*TODO*///		{
/*TODO*///			auto iter = m_slot_options.find(slot_opt.first);
/*TODO*///			if (iter != m_slot_options.end())
/*TODO*///				iter->second.specify(std::move(slot_opt.second));
/*TODO*///			else
/*TODO*///				deferred_opts.slot[slot_opt.first] = std::move(slot_opt.second);
/*TODO*///		}
/*TODO*///
/*TODO*///		// distribute image options
/*TODO*///		for (auto &image_opt : softlist_opts.image)
/*TODO*///		{
/*TODO*///			auto iter = m_image_options.find(image_opt.first);
/*TODO*///			if (iter != m_image_options.end())
/*TODO*///				iter->second->specify(std::move(image_opt.second));
/*TODO*///			else
/*TODO*///				deferred_opts.image[image_opt.first] = std::move(image_opt.second);
/*TODO*///		}
/*TODO*///
/*TODO*///		// keep any deferred options for the next round
/*TODO*///		softlist_opts = std::move(deferred_opts);
/*TODO*///
/*TODO*///		// do we have any pending options after failing to distribute any?
/*TODO*///		size_t after_size = softlist_opts.slot.size() + softlist_opts.image.size();
/*TODO*///		if ((after_size > 0) && after_size >= before_size)
/*TODO*///			throw options_error_exception("Could not assign software option");
/*TODO*///	}
/*TODO*///
/*TODO*///	// we've succeeded; update the set name
/*TODO*///	m_software_name = std::move(new_software);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  evaluate_initial_softlist_options
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///emu_options::software_options emu_options::evaluate_initial_softlist_options(const std::string &software_identifier)
/*TODO*///{
/*TODO*///	software_options results;
/*TODO*///
/*TODO*///	// load software specified at the command line (if any of course)
/*TODO*///	if (!software_identifier.empty())
/*TODO*///	{
/*TODO*///		// we have software; first identify the proper game_driver
/*TODO*///		if (!m_system)
/*TODO*///			throw options_error_exception("Cannot specify software without specifying system");
/*TODO*///
/*TODO*///		// and set up a configuration
/*TODO*///		machine_config config(*m_system, *this);
/*TODO*///		software_list_device_iterator iter(config.root_device());
/*TODO*///		if (iter.count() == 0)
/*TODO*///			throw emu_fatalerror(EMU_ERR_FATALERROR, "Error: unknown option: %s\n", software_identifier.c_str());
/*TODO*///
/*TODO*///		// and finally set up the stack
/*TODO*///		std::stack<std::string> software_identifier_stack;
/*TODO*///		software_identifier_stack.push(software_identifier);
/*TODO*///
/*TODO*///		// we need to keep evaluating softlist identifiers until the stack is empty
/*TODO*///		while (!software_identifier_stack.empty())
/*TODO*///		{
/*TODO*///			// pop the identifier
/*TODO*///			std::string current_software_identifier = std::move(software_identifier_stack.top());
/*TODO*///			software_identifier_stack.pop();
/*TODO*///
/*TODO*///			// and parse it
/*TODO*///			std::string list_name, software_name;
/*TODO*///			auto colon_pos = current_software_identifier.find_first_of(':');
/*TODO*///			if (colon_pos != std::string::npos)
/*TODO*///			{
/*TODO*///				list_name = current_software_identifier.substr(0, colon_pos);
/*TODO*///				software_name = current_software_identifier.substr(colon_pos + 1);
/*TODO*///			}
/*TODO*///			else
/*TODO*///			{
/*TODO*///				software_name = current_software_identifier;
/*TODO*///			}
/*TODO*///
/*TODO*///			// loop through all softlist devices, and try to find one capable of handling the requested software
/*TODO*///			bool found = false;
/*TODO*///			bool compatible = false;
/*TODO*///			for (software_list_device &swlistdev : iter)
/*TODO*///			{
/*TODO*///				if (list_name.empty() || (list_name == swlistdev.list_name()))
/*TODO*///				{
/*TODO*///					const software_info *swinfo = swlistdev.find(software_name);
/*TODO*///					if (swinfo != nullptr)
/*TODO*///					{
/*TODO*///						// loop through all parts
/*TODO*///						for (const software_part &swpart : swinfo->parts())
/*TODO*///						{
/*TODO*///							// only load compatible software this way
/*TODO*///							if (swlistdev.is_compatible(swpart) == SOFTWARE_IS_COMPATIBLE)
/*TODO*///							{
/*TODO*///								// we need to find a mountable image slot, but we need to ensure it is a slot
/*TODO*///								// for which we have not already distributed a part to
/*TODO*///								device_image_interface *image = software_list_device::find_mountable_image(
/*TODO*///									config,
/*TODO*///									swpart,
/*TODO*///									[&results](const device_image_interface &candidate) { return results.image.count(candidate.instance_name()) == 0; });
/*TODO*///
/*TODO*///								// did we find a slot to put this part into?
/*TODO*///								if (image != nullptr)
/*TODO*///								{
/*TODO*///									// we've resolved this software
/*TODO*///									results.image[image->instance_name()] = string_format("%s:%s:%s", swlistdev.list_name(), software_name, swpart.name());
/*TODO*///
/*TODO*///									// does this software part have a requirement on another part?
/*TODO*///									const char *requirement = swpart.feature("requirement");
/*TODO*///									if (requirement)
/*TODO*///										software_identifier_stack.push(requirement);
/*TODO*///								}
/*TODO*///								compatible = true;
/*TODO*///							}
/*TODO*///							found = true;
/*TODO*///						}
/*TODO*///
/*TODO*///						// identify other shared features specified as '<<slot name>>_default'
/*TODO*///						//
/*TODO*///						// example from SMS:
/*TODO*///						//
/*TODO*///						//  <software name = "alexbmx">
/*TODO*///						//      ...
/*TODO*///						//      <sharedfeat name = "ctrl1_default" value = "paddle" />
/*TODO*///						//  </software>
/*TODO*///						for (const feature_list_item &fi : swinfo->shared_info())
/*TODO*///						{
/*TODO*///							const std::string default_suffix = "_default";
/*TODO*///							if (fi.name().size() > default_suffix.size()
/*TODO*///								&& fi.name().compare(fi.name().size() - default_suffix.size(), default_suffix.size(), default_suffix) == 0)
/*TODO*///							{
/*TODO*///								std::string slot_name = fi.name().substr(0, fi.name().size() - default_suffix.size());
/*TODO*///								results.slot[slot_name] = fi.value();
/*TODO*///							}
/*TODO*///						}
/*TODO*///					}
/*TODO*///				}
/*TODO*///				if (compatible)
/*TODO*///					break;
/*TODO*///			}
/*TODO*///
/*TODO*///			if (!compatible)
/*TODO*///			{
/*TODO*///				software_list_device::display_matches(config, nullptr, software_name);
/*TODO*///
/*TODO*///				// The text of this options_error_exception() is then passed to osd_printf_error() in cli_frontend::execute().  Therefore, it needs
/*TODO*///				// to be human readable text.  We want to snake through a message about software incompatibility while being silent if that is not
/*TODO*///				// the case.
/*TODO*///				//
/*TODO*///				// Arguably, anything related to user-visible text should really be done within src/frontend.  The invocation of
/*TODO*///				// software_list_device::display_matches() should really be done there as well
/*TODO*///				if (!found)
/*TODO*///					throw options_error_exception("");
/*TODO*///				else
/*TODO*///					throw options_error_exception("Software '%s' is incompatible with system '%s'\n", software_name, m_system->name);
/*TODO*///			}
/*TODO*///		}
/*TODO*///	}
/*TODO*///	return results;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  find_slot_option
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///const slot_option *emu_options::find_slot_option(const std::string &device_name) const
/*TODO*///{
/*TODO*///	auto iter = m_slot_options.find(device_name);
/*TODO*///	return iter != m_slot_options.end() ? &iter->second : nullptr;
/*TODO*///}
/*TODO*///
/*TODO*///slot_option *emu_options::find_slot_option(const std::string &device_name)
/*TODO*///{
/*TODO*///	auto iter = m_slot_options.find(device_name);
/*TODO*///	return iter != m_slot_options.end() ? &iter->second : nullptr;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  slot_option
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///const slot_option &emu_options::slot_option(const std::string &device_name) const
/*TODO*///{
/*TODO*///	const ::slot_option *opt = find_slot_option(device_name);
/*TODO*///	assert(opt && "Attempt to access non-existent slot option");
/*TODO*///	return *opt;
/*TODO*///}
/*TODO*///
/*TODO*///slot_option &emu_options::slot_option(const std::string &device_name)
/*TODO*///{
/*TODO*///	::slot_option *opt = find_slot_option(device_name);
/*TODO*///	assert(opt && "Attempt to access non-existent slot option");
/*TODO*///	return *opt;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  image_option
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///const image_option &emu_options::image_option(const std::string &device_name) const
/*TODO*///{
/*TODO*///	auto iter = m_image_options.find(device_name);
/*TODO*///	assert(iter != m_image_options.end() && "Attempt to access non-existent image option");
/*TODO*///	return *iter->second;
/*TODO*///}
/*TODO*///
/*TODO*///image_option &emu_options::image_option(const std::string &device_name)
/*TODO*///{
/*TODO*///	auto iter = m_image_options.find(device_name);
/*TODO*///	assert(iter != m_image_options.end() && "Attempt to access non-existent image option");
/*TODO*///	return *iter->second;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  command_argument_processed
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///void emu_options::command_argument_processed()
/*TODO*///{
/*TODO*///	// some command line arguments require that the system name be set, so we can get slot options
/*TODO*///	if (command_arguments().size() == 1 && !core_iswildstr(command_arguments()[0].c_str()) &&
/*TODO*///		(command() == "listdevices" || (command() == "listslots") || (command() == "listmedia") || (command() == "listsoftware")))
/*TODO*///	{
/*TODO*///		set_system_name(command_arguments()[0]);
/*TODO*///	}
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////**************************************************************************
/*TODO*/////  SLOT OPTIONS
/*TODO*/////**************************************************************************
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  slot_option ctor
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///slot_option::slot_option(emu_options &host, const char *default_value)
/*TODO*///	: m_host(host)
/*TODO*///	, m_specified(false)
/*TODO*///	, m_default_value(default_value ? default_value : "")
/*TODO*///{
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  slot_option::value
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///const std::string &slot_option::value() const
/*TODO*///{
/*TODO*///	// There are a number of ways that the value can be determined; there
/*TODO*///	// is a specific order of precedence:
/*TODO*///	//
/*TODO*///	//  1.  Highest priority is whatever may have been specified by the user (whether it
/*TODO*///	//      was specified at the command line, an INI file, or in the UI).  We keep track
/*TODO*///	//      of whether these values were specified this way
/*TODO*///	//
/*TODO*///	//      Take note that slots have a notion of being "selectable".  Slots that are not
/*TODO*///	//      marked as selectable cannot be specified with this technique
/*TODO*///	//
/*TODO*///	//  2.  Next highest is what is returned from get_default_card_software()
/*TODO*///	//
/*TODO*///	//  3.  Last in priority is what was specified as the slot default.  This comes from
/*TODO*///	//      device setup
/*TODO*///	if (m_specified)
/*TODO*///		return m_specified_value;
/*TODO*///	else if (!m_default_card_software.empty())
/*TODO*///		return m_default_card_software;
/*TODO*///	else
/*TODO*///		return m_default_value;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  slot_option::specified_value
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///std::string slot_option::specified_value() const
/*TODO*///{
/*TODO*///	std::string result;
/*TODO*///	if (m_specified)
/*TODO*///	{
/*TODO*///		result = m_specified_bios.empty()
/*TODO*///			? m_specified_value
/*TODO*///			: util::string_format("%s,bios=%s", m_specified_value, m_specified_bios);
/*TODO*///	}
/*TODO*///	return result;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  slot_option::specify
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///void slot_option::specify(std::string &&text, bool peg_priority)
/*TODO*///{
/*TODO*///	// record the old value; we may need to trigger an update
/*TODO*///	const std::string old_value = value();
/*TODO*///
/*TODO*///	// we need to do some elementary parsing here
/*TODO*///	const char *bios_arg = ",bios=";
/*TODO*///	const size_t pos = text.find(bios_arg);
/*TODO*///	if (pos != std::string::npos)
/*TODO*///	{
/*TODO*///		m_specified = true;
/*TODO*///		m_specified_value = text.substr(0, pos);
/*TODO*///		m_specified_bios = text.substr(pos + strlen(bios_arg));
/*TODO*///	}
/*TODO*///	else
/*TODO*///	{
/*TODO*///		m_specified = true;
/*TODO*///		m_specified_value = std::move(text);
/*TODO*///		m_specified_bios = "";
/*TODO*///	}
/*TODO*///
/*TODO*///	conditionally_peg_priority(m_entry, peg_priority);
/*TODO*///
/*TODO*///	// we may have changed
/*TODO*///	possibly_changed(old_value);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  slot_option::specify
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///void slot_option::specify(const std::string &text, bool peg_priority)
/*TODO*///{
/*TODO*///	specify(std::string(text), peg_priority);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  slot_option::set_default_card_software
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///void slot_option::set_default_card_software(std::string &&s)
/*TODO*///{
/*TODO*///	// record the old value; we may need to trigger an update
/*TODO*///	const std::string old_value = value();
/*TODO*///
/*TODO*///	// update the default card software
/*TODO*///	m_default_card_software = std::move(s);
/*TODO*///
/*TODO*///	// we may have changed
/*TODO*///	possibly_changed(old_value);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  slot_option::possibly_changed
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///void slot_option::possibly_changed(const std::string &old_value)
/*TODO*///{
/*TODO*///	if (value() != old_value)
/*TODO*///		m_host.update_slot_and_image_options();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  slot_option::set_bios
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///void slot_option::set_bios(std::string &&text)
/*TODO*///{
/*TODO*///	if (!m_specified)
/*TODO*///	{
/*TODO*///		m_specified = true;
/*TODO*///		m_specified_value = value();
/*TODO*///	}
/*TODO*///	m_specified_bios = std::move(text);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  slot_option::setup_option_entry
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///core_options::entry::shared_ptr slot_option::setup_option_entry(const char *name)
/*TODO*///{
/*TODO*///	// this should only be called once
/*TODO*///	assert(m_entry.expired());
/*TODO*///
/*TODO*///	// create the entry and return it
/*TODO*///	core_options::entry::shared_ptr entry = std::make_shared<slot_option_entry>(name, *this);
/*TODO*///	m_entry = entry;
/*TODO*///	return entry;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////**************************************************************************
/*TODO*/////  IMAGE OPTIONS
/*TODO*/////**************************************************************************
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  image_option ctor
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///image_option::image_option(emu_options &host, const std::string &canonical_instance_name)
/*TODO*///	: m_host(host)
/*TODO*///	, m_canonical_instance_name(canonical_instance_name)
/*TODO*///{
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  image_option::specify
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///void image_option::specify(const std::string &value, bool peg_priority)
/*TODO*///{
/*TODO*///	if (value != m_value)
/*TODO*///	{
/*TODO*///		m_value = value;
/*TODO*///		m_host.reevaluate_default_card_software();
/*TODO*///	}
/*TODO*///	conditionally_peg_priority(m_entry, peg_priority);
/*TODO*///}
/*TODO*///
/*TODO*///void image_option::specify(std::string &&value, bool peg_priority)
/*TODO*///{
/*TODO*///	if (value != m_value)
/*TODO*///	{
/*TODO*///		m_value = std::move(value);
/*TODO*///		m_host.reevaluate_default_card_software();
/*TODO*///	}
/*TODO*///	conditionally_peg_priority(m_entry, peg_priority);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  image_option::setup_option_entry
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///core_options::entry::shared_ptr image_option::setup_option_entry(std::vector<std::string> &&names)
/*TODO*///{
/*TODO*///	// this should only be called once
/*TODO*///	assert(m_entry.expired());
/*TODO*///
/*TODO*///	// create the entry and return it
/*TODO*///	core_options::entry::shared_ptr entry = std::make_shared<image_option_entry>(std::move(names), *this);
/*TODO*///	m_entry = entry;
/*TODO*///	return entry;
/*TODO*///}
    
}
