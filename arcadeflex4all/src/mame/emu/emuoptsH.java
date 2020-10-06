package mame.emu;

public class emuoptsH {
/*TODO*///// license:BSD-3-Clause
/*TODO*///// copyright-holders:Aaron Giles
/*TODO*////***************************************************************************
/*TODO*///
/*TODO*///    emuopts.h
/*TODO*///
/*TODO*///    Options file and command line management.
/*TODO*///
/*TODO*///***************************************************************************/
/*TODO*///
/*TODO*///#ifndef MAME_EMU_EMUOPTS_H
/*TODO*///#define MAME_EMU_EMUOPTS_H
/*TODO*///
/*TODO*///#pragma once
/*TODO*///
/*TODO*///#include "options.h"
/*TODO*///
/*TODO*///#define OPTION_PRIORITY_CMDLINE     OPTION_PRIORITY_HIGH + 1
/*TODO*///// core options
/*TODO*///#define OPTION_SYSTEMNAME           core_options::unadorned(0)
/*TODO*///#define OPTION_SOFTWARENAME         core_options::unadorned(1)
/*TODO*///
/*TODO*///// core configuration options
/*TODO*///#define OPTION_READCONFIG           "readconfig"
/*TODO*///#define OPTION_WRITECONFIG          "writeconfig"
/*TODO*///
/*TODO*///// core search path options
/*TODO*///#define OPTION_HOMEPATH             "homepath"
/*TODO*///#define OPTION_MEDIAPATH            "rompath"
/*TODO*///#define OPTION_HASHPATH             "hashpath"
/*TODO*///#define OPTION_SAMPLEPATH           "samplepath"
/*TODO*///#define OPTION_ARTPATH              "artpath"
/*TODO*///#define OPTION_CTRLRPATH            "ctrlrpath"
/*TODO*///#define OPTION_INIPATH              "inipath"
/*TODO*///#define OPTION_FONTPATH             "fontpath"
/*TODO*///#define OPTION_CHEATPATH            "cheatpath"
/*TODO*///#define OPTION_CROSSHAIRPATH        "crosshairpath"
/*TODO*///#define OPTION_PLUGINSPATH          "pluginspath"
/*TODO*///#define OPTION_LANGUAGEPATH         "languagepath"
/*TODO*///#define OPTION_SWPATH               "swpath"
/*TODO*///
/*TODO*///// core directory options
/*TODO*///#define OPTION_CFG_DIRECTORY        "cfg_directory"
/*TODO*///#define OPTION_NVRAM_DIRECTORY      "nvram_directory"
/*TODO*///#define OPTION_INPUT_DIRECTORY      "input_directory"
/*TODO*///#define OPTION_STATE_DIRECTORY      "state_directory"
/*TODO*///#define OPTION_SNAPSHOT_DIRECTORY   "snapshot_directory"
/*TODO*///#define OPTION_DIFF_DIRECTORY       "diff_directory"
/*TODO*///#define OPTION_COMMENT_DIRECTORY    "comment_directory"
/*TODO*///
/*TODO*///// core state/playback options
/*TODO*///#define OPTION_STATE                "state"
/*TODO*///#define OPTION_AUTOSAVE             "autosave"
/*TODO*///#define OPTION_REWIND               "rewind"
/*TODO*///#define OPTION_REWIND_CAPACITY      "rewind_capacity"
/*TODO*///#define OPTION_PLAYBACK             "playback"
/*TODO*///#define OPTION_RECORD               "record"
/*TODO*///#define OPTION_RECORD_TIMECODE      "record_timecode"
/*TODO*///#define OPTION_EXIT_AFTER_PLAYBACK  "exit_after_playback"
/*TODO*///#define OPTION_MNGWRITE             "mngwrite"
/*TODO*///#define OPTION_AVIWRITE             "aviwrite"
/*TODO*///#define OPTION_WAVWRITE             "wavwrite"
/*TODO*///#define OPTION_SNAPNAME             "snapname"
/*TODO*///#define OPTION_SNAPSIZE             "snapsize"
/*TODO*///#define OPTION_SNAPVIEW             "snapview"
/*TODO*///#define OPTION_SNAPBILINEAR         "snapbilinear"
/*TODO*///#define OPTION_STATENAME            "statename"
/*TODO*///#define OPTION_BURNIN               "burnin"
/*TODO*///
/*TODO*///// core performance options
/*TODO*///#define OPTION_AUTOFRAMESKIP        "autoframeskip"
/*TODO*///#define OPTION_FRAMESKIP            "frameskip"
/*TODO*///#define OPTION_SECONDS_TO_RUN       "seconds_to_run"
/*TODO*///#define OPTION_THROTTLE             "throttle"
/*TODO*///#define OPTION_SLEEP                "sleep"
/*TODO*///#define OPTION_SPEED                "speed"
/*TODO*///#define OPTION_REFRESHSPEED         "refreshspeed"
/*TODO*///#define OPTION_LOWLATENCY           "lowlatency"
/*TODO*///
/*TODO*///// core render options
/*TODO*///#define OPTION_KEEPASPECT           "keepaspect"
/*TODO*///#define OPTION_UNEVENSTRETCH        "unevenstretch"
/*TODO*///#define OPTION_UNEVENSTRETCHX       "unevenstretchx"
/*TODO*///#define OPTION_UNEVENSTRETCHY       "unevenstretchy"
/*TODO*///#define OPTION_AUTOSTRETCHXY        "autostretchxy"
/*TODO*///#define OPTION_INTOVERSCAN          "intoverscan"
/*TODO*///#define OPTION_INTSCALEX            "intscalex"
/*TODO*///#define OPTION_INTSCALEY            "intscaley"
/*TODO*///
/*TODO*///// core rotation options
/*TODO*///#define OPTION_ROTATE               "rotate"
/*TODO*///#define OPTION_ROR                  "ror"
/*TODO*///#define OPTION_ROL                  "rol"
/*TODO*///#define OPTION_AUTOROR              "autoror"
/*TODO*///#define OPTION_AUTOROL              "autorol"
/*TODO*///#define OPTION_FLIPX                "flipx"
/*TODO*///#define OPTION_FLIPY                "flipy"
/*TODO*///
/*TODO*///// core artwork options
/*TODO*///#define OPTION_ARTWORK_CROP         "artwork_crop"
/*TODO*///#define OPTION_FALLBACK_ARTWORK     "fallback_artwork"
/*TODO*///#define OPTION_OVERRIDE_ARTWORK     "override_artwork"
/*TODO*///
/*TODO*///// core screen options
/*TODO*///#define OPTION_BRIGHTNESS           "brightness"
/*TODO*///#define OPTION_CONTRAST             "contrast"
/*TODO*///#define OPTION_GAMMA                "gamma"
/*TODO*///#define OPTION_PAUSE_BRIGHTNESS     "pause_brightness"
/*TODO*///#define OPTION_EFFECT               "effect"
/*TODO*///
/*TODO*///// core vector options
/*TODO*///#define OPTION_BEAM_WIDTH_MIN       "beam_width_min"
/*TODO*///#define OPTION_BEAM_WIDTH_MAX       "beam_width_max"
/*TODO*///#define OPTION_BEAM_DOT_SIZE        "beam_dot_size"
/*TODO*///#define OPTION_BEAM_INTENSITY_WEIGHT   "beam_intensity_weight"
/*TODO*///#define OPTION_FLICKER              "flicker"
/*TODO*///
/*TODO*///// core sound options
/*TODO*///#define OPTION_SAMPLERATE           "samplerate"
/*TODO*///#define OPTION_SAMPLES              "samples"
/*TODO*///#define OPTION_VOLUME               "volume"
/*TODO*///#define OPTION_SPEAKER_REPORT       "speaker_report"
/*TODO*///
/*TODO*///// core input options
/*TODO*///#define OPTION_COIN_LOCKOUT         "coin_lockout"
/*TODO*///#define OPTION_CTRLR                "ctrlr"
/*TODO*///#define OPTION_MOUSE                "mouse"
/*TODO*///#define OPTION_JOYSTICK             "joystick"
/*TODO*///#define OPTION_LIGHTGUN             "lightgun"
/*TODO*///#define OPTION_MULTIKEYBOARD        "multikeyboard"
/*TODO*///#define OPTION_MULTIMOUSE           "multimouse"
/*TODO*///#define OPTION_STEADYKEY            "steadykey"
/*TODO*///#define OPTION_UI_ACTIVE            "ui_active"
/*TODO*///#define OPTION_OFFSCREEN_RELOAD     "offscreen_reload"
/*TODO*///#define OPTION_JOYSTICK_MAP         "joystick_map"
/*TODO*///#define OPTION_JOYSTICK_DEADZONE    "joystick_deadzone"
/*TODO*///#define OPTION_JOYSTICK_SATURATION  "joystick_saturation"
/*TODO*///#define OPTION_NATURAL_KEYBOARD     "natural"
/*TODO*///#define OPTION_JOYSTICK_CONTRADICTORY   "joystick_contradictory"
/*TODO*///#define OPTION_COIN_IMPULSE         "coin_impulse"
/*TODO*///
/*TODO*///// input autoenable options
/*TODO*///#define OPTION_PADDLE_DEVICE        "paddle_device"
/*TODO*///#define OPTION_ADSTICK_DEVICE       "adstick_device"
/*TODO*///#define OPTION_PEDAL_DEVICE         "pedal_device"
/*TODO*///#define OPTION_DIAL_DEVICE          "dial_device"
/*TODO*///#define OPTION_TRACKBALL_DEVICE     "trackball_device"
/*TODO*///#define OPTION_LIGHTGUN_DEVICE      "lightgun_device"
/*TODO*///#define OPTION_POSITIONAL_DEVICE    "positional_device"
/*TODO*///#define OPTION_MOUSE_DEVICE         "mouse_device"
/*TODO*///
/*TODO*///// core debugging options
/*TODO*///#define OPTION_LOG                  "log"
/*TODO*///#define OPTION_DEBUG                "debug"
/*TODO*///#define OPTION_VERBOSE              "verbose"
/*TODO*///#define OPTION_OSLOG                "oslog"
/*TODO*///#define OPTION_UPDATEINPAUSE        "update_in_pause"
/*TODO*///#define OPTION_DEBUGSCRIPT          "debugscript"
/*TODO*///#define OPTION_DEBUGLOG             "debuglog"
/*TODO*///
/*TODO*///// core misc options
/*TODO*///#define OPTION_DRC                  "drc"
/*TODO*///#define OPTION_DRC_USE_C            "drc_use_c"
/*TODO*///#define OPTION_DRC_LOG_UML          "drc_log_uml"
/*TODO*///#define OPTION_DRC_LOG_NATIVE       "drc_log_native"
/*TODO*///#define OPTION_BIOS                 "bios"
/*TODO*///#define OPTION_CHEAT                "cheat"
/*TODO*///#define OPTION_SKIP_GAMEINFO        "skip_gameinfo"
/*TODO*///#define OPTION_UI_FONT              "uifont"
/*TODO*///#define OPTION_UI                   "ui"
/*TODO*///#define OPTION_RAMSIZE              "ramsize"
/*TODO*///#define OPTION_NVRAM_SAVE           "nvram_save"
/*TODO*///
/*TODO*///// core comm options
/*TODO*///#define OPTION_COMM_LOCAL_HOST      "comm_localhost"
/*TODO*///#define OPTION_COMM_LOCAL_PORT      "comm_localport"
/*TODO*///#define OPTION_COMM_REMOTE_HOST     "comm_remotehost"
/*TODO*///#define OPTION_COMM_REMOTE_PORT     "comm_remoteport"
/*TODO*///#define OPTION_COMM_FRAME_SYNC      "comm_framesync"
/*TODO*///
/*TODO*///#define OPTION_CONFIRM_QUIT         "confirm_quit"
/*TODO*///#define OPTION_UI_MOUSE             "ui_mouse"
/*TODO*///
/*TODO*///#define OPTION_AUTOBOOT_COMMAND     "autoboot_command"
/*TODO*///#define OPTION_AUTOBOOT_DELAY       "autoboot_delay"
/*TODO*///#define OPTION_AUTOBOOT_SCRIPT      "autoboot_script"
/*TODO*///
/*TODO*///#define OPTION_CONSOLE              "console"
/*TODO*///#define OPTION_PLUGINS              "plugins"
/*TODO*///#define OPTION_PLUGIN               "plugin"
/*TODO*///#define OPTION_NO_PLUGIN            "noplugin"
/*TODO*///
/*TODO*///#define OPTION_LANGUAGE             "language"
/*TODO*///
/*TODO*///#define OPTION_HTTP                 "http"
/*TODO*///#define OPTION_HTTP_PORT            "http_port"
/*TODO*///#define OPTION_HTTP_ROOT            "http_root"
/*TODO*///
/*TODO*/////**************************************************************************
/*TODO*/////  TYPE DEFINITIONS
/*TODO*/////**************************************************************************
/*TODO*///
/*TODO*///class game_driver;
/*TODO*///class device_slot_interface;
/*TODO*///class emu_options;
/*TODO*///
/*TODO*///class slot_option
/*TODO*///{
/*TODO*///public:
/*TODO*///	slot_option(emu_options &host, const char *default_value);
/*TODO*///	slot_option(const slot_option &that) = delete;
/*TODO*///	slot_option(slot_option &&that) = default;
/*TODO*///
/*TODO*///	// accessors
/*TODO*///	const std::string &value() const;
/*TODO*///	std::string specified_value() const;
/*TODO*///	const std::string &bios() const { return m_specified_bios; }
/*TODO*///	const std::string &default_card_software() const { return m_default_card_software; }
/*TODO*///	bool specified() const { return m_specified; }
/*TODO*///	core_options::entry::shared_ptr option_entry() const { return m_entry.lock(); }
/*TODO*///
/*TODO*///	// seters
/*TODO*///	void specify(const std::string &text, bool peg_priority = true);
/*TODO*///	void specify(std::string &&text, bool peg_priority = true);
/*TODO*///	void set_bios(std::string &&text);
/*TODO*///	void set_default_card_software(std::string &&s);
/*TODO*///
/*TODO*///	// instantiates an option entry (don't call outside of emuopts.cpp)
/*TODO*///	core_options::entry::shared_ptr setup_option_entry(const char *name);
/*TODO*///
/*TODO*///private:
/*TODO*///	void possibly_changed(const std::string &old_value);
/*TODO*///
/*TODO*///	emu_options &                   m_host;
/*TODO*///	bool                            m_specified;
/*TODO*///	std::string                     m_specified_value;
/*TODO*///	std::string                     m_specified_bios;
/*TODO*///	std::string                     m_default_card_software;
/*TODO*///	std::string                     m_default_value;
/*TODO*///	core_options::entry::weak_ptr   m_entry;
/*TODO*///};
/*TODO*///
/*TODO*///
/*TODO*///class image_option
/*TODO*///{
/*TODO*///public:
/*TODO*///	image_option(emu_options &host, const std::string &canonical_instance_name);
/*TODO*///	image_option(const image_option &that) = delete;
/*TODO*///	image_option(image_option &&that) = default;
/*TODO*///
/*TODO*///	// accessors
/*TODO*///	const std::string &canonical_instance_name() const { return m_canonical_instance_name; }
/*TODO*///	const std::string &value() const { return m_value; }
/*TODO*///	core_options::entry::shared_ptr option_entry() const { return m_entry.lock(); }
/*TODO*///
/*TODO*///	// mutators
/*TODO*///	void specify(const std::string &value, bool peg_priority = true);
/*TODO*///	void specify(std::string &&value, bool peg_priority = true);
/*TODO*///
/*TODO*///	// instantiates an option entry (don't call outside of emuopts.cpp)
/*TODO*///	core_options::entry::shared_ptr setup_option_entry(std::vector<std::string> &&names);
/*TODO*///
/*TODO*///private:
/*TODO*///	emu_options &                   m_host;
/*TODO*///	std::string                     m_canonical_instance_name;
/*TODO*///	std::string                     m_value;
/*TODO*///	core_options::entry::weak_ptr   m_entry;
/*TODO*///};
/*TODO*///
/*TODO*///
/*TODO*///class emu_options : public core_options
/*TODO*///{
/*TODO*///	friend class slot_option;
/*TODO*///	friend class image_option;
/*TODO*///public:
/*TODO*///	enum ui_option
/*TODO*///	{
/*TODO*///		UI_CABINET,
/*TODO*///		UI_SIMPLE
/*TODO*///	};

	public enum option_support
	{
		FULL,                   // full option support
		GENERAL_AND_SYSTEM,     // support for general options and system (no softlist)
		GENERAL_ONLY            // only support for general options
	};

/*TODO*///	// construction/destruction
/*TODO*///	emu_options(option_support support = option_support::FULL);
/*TODO*///	~emu_options();
/*TODO*///
/*TODO*///	// mutation
/*TODO*///	void set_system_name(const std::string &new_system_name);
/*TODO*///	void set_system_name(std::string &&new_system_name);
/*TODO*///	void set_software(std::string &&new_software);
/*TODO*///
/*TODO*///	// core options
/*TODO*///	const game_driver *system() const { return m_system; }
/*TODO*///	const char *system_name() const;
/*TODO*///	const std::string &attempted_system_name() const { return m_attempted_system_name; }
/*TODO*///	const std::string &software_name() const { return m_software_name; }
/*TODO*///
/*TODO*///	// core configuration options
/*TODO*///	bool read_config() const { return bool_value(OPTION_READCONFIG); }
/*TODO*///	bool write_config() const { return bool_value(OPTION_WRITECONFIG); }
/*TODO*///
/*TODO*///	// core search path options
/*TODO*///	const char *home_path() const { return value(OPTION_HOMEPATH); }
/*TODO*///	const char *media_path() const { return value(OPTION_MEDIAPATH); }
/*TODO*///	const char *hash_path() const { return value(OPTION_HASHPATH); }
/*TODO*///	const char *sample_path() const { return value(OPTION_SAMPLEPATH); }
/*TODO*///	const char *art_path() const { return value(OPTION_ARTPATH); }
/*TODO*///	const char *ctrlr_path() const { return value(OPTION_CTRLRPATH); }
/*TODO*///	const char *ini_path() const { return value(OPTION_INIPATH); }
/*TODO*///	const char *font_path() const { return value(OPTION_FONTPATH); }
/*TODO*///	const char *cheat_path() const { return value(OPTION_CHEATPATH); }
/*TODO*///	const char *crosshair_path() const { return value(OPTION_CROSSHAIRPATH); }
/*TODO*///	const char *plugins_path() const { return value(OPTION_PLUGINSPATH); }
/*TODO*///	const char *language_path() const { return value(OPTION_LANGUAGEPATH); }
/*TODO*///	const char *sw_path() const { return value(OPTION_SWPATH); }
/*TODO*///
/*TODO*///	// core directory options
/*TODO*///	const char *cfg_directory() const { return value(OPTION_CFG_DIRECTORY); }
/*TODO*///	const char *nvram_directory() const { return value(OPTION_NVRAM_DIRECTORY); }
/*TODO*///	const char *input_directory() const { return value(OPTION_INPUT_DIRECTORY); }
/*TODO*///	const char *state_directory() const { return value(OPTION_STATE_DIRECTORY); }
/*TODO*///	const char *snapshot_directory() const { return value(OPTION_SNAPSHOT_DIRECTORY); }
/*TODO*///	const char *diff_directory() const { return value(OPTION_DIFF_DIRECTORY); }
/*TODO*///	const char *comment_directory() const { return value(OPTION_COMMENT_DIRECTORY); }
/*TODO*///
/*TODO*///	// core state/playback options
/*TODO*///	const char *state() const { return value(OPTION_STATE); }
/*TODO*///	bool autosave() const { return bool_value(OPTION_AUTOSAVE); }
/*TODO*///	int rewind() const { return bool_value(OPTION_REWIND); }
/*TODO*///	int rewind_capacity() const { return int_value(OPTION_REWIND_CAPACITY); }
/*TODO*///	const char *playback() const { return value(OPTION_PLAYBACK); }
/*TODO*///	const char *record() const { return value(OPTION_RECORD); }
/*TODO*///	bool record_timecode() const { return bool_value(OPTION_RECORD_TIMECODE); }
/*TODO*///	bool exit_after_playback() const { return bool_value(OPTION_EXIT_AFTER_PLAYBACK); }
/*TODO*///	const char *mng_write() const { return value(OPTION_MNGWRITE); }
/*TODO*///	const char *avi_write() const { return value(OPTION_AVIWRITE); }
/*TODO*///	const char *wav_write() const { return value(OPTION_WAVWRITE); }
/*TODO*///	const char *snap_name() const { return value(OPTION_SNAPNAME); }
/*TODO*///	const char *snap_size() const { return value(OPTION_SNAPSIZE); }
/*TODO*///	const char *snap_view() const { return value(OPTION_SNAPVIEW); }
/*TODO*///	bool snap_bilinear() const { return bool_value(OPTION_SNAPBILINEAR); }
/*TODO*///	const char *state_name() const { return value(OPTION_STATENAME); }
/*TODO*///	bool burnin() const { return bool_value(OPTION_BURNIN); }
/*TODO*///
/*TODO*///	// core performance options
/*TODO*///	bool auto_frameskip() const { return bool_value(OPTION_AUTOFRAMESKIP); }
/*TODO*///	int frameskip() const { return int_value(OPTION_FRAMESKIP); }
/*TODO*///	int seconds_to_run() const { return int_value(OPTION_SECONDS_TO_RUN); }
/*TODO*///	bool throttle() const { return bool_value(OPTION_THROTTLE); }
/*TODO*///	bool sleep() const { return m_sleep; }
/*TODO*///	float speed() const { return float_value(OPTION_SPEED); }
/*TODO*///	bool refresh_speed() const { return m_refresh_speed; }
/*TODO*///	bool low_latency() const { return bool_value(OPTION_LOWLATENCY); }
/*TODO*///
/*TODO*///	// core render options
/*TODO*///	bool keep_aspect() const { return bool_value(OPTION_KEEPASPECT); }
/*TODO*///	bool uneven_stretch() const { return bool_value(OPTION_UNEVENSTRETCH); }
/*TODO*///	bool uneven_stretch_x() const { return bool_value(OPTION_UNEVENSTRETCHX); }
/*TODO*///	bool uneven_stretch_y() const { return bool_value(OPTION_UNEVENSTRETCHY); }
/*TODO*///	bool auto_stretch_xy() const { return bool_value(OPTION_AUTOSTRETCHXY); }
/*TODO*///	bool int_overscan() const { return bool_value(OPTION_INTOVERSCAN); }
/*TODO*///	int int_scale_x() const { return int_value(OPTION_INTSCALEX); }
/*TODO*///	int int_scale_y() const { return int_value(OPTION_INTSCALEY); }
/*TODO*///
/*TODO*///	// core rotation options
/*TODO*///	bool rotate() const { return bool_value(OPTION_ROTATE); }
/*TODO*///	bool ror() const { return bool_value(OPTION_ROR); }
/*TODO*///	bool rol() const { return bool_value(OPTION_ROL); }
/*TODO*///	bool auto_ror() const { return bool_value(OPTION_AUTOROR); }
/*TODO*///	bool auto_rol() const { return bool_value(OPTION_AUTOROL); }
/*TODO*///	bool flipx() const { return bool_value(OPTION_FLIPX); }
/*TODO*///	bool flipy() const { return bool_value(OPTION_FLIPY); }
/*TODO*///
/*TODO*///	// core artwork options
/*TODO*///	bool artwork_crop() const { return bool_value(OPTION_ARTWORK_CROP); }
/*TODO*///	const char *fallback_artwork() const { return value(OPTION_FALLBACK_ARTWORK); }
/*TODO*///	const char *override_artwork() const { return value(OPTION_OVERRIDE_ARTWORK); }
/*TODO*///
/*TODO*///	// core screen options
/*TODO*///	float brightness() const { return float_value(OPTION_BRIGHTNESS); }
/*TODO*///	float contrast() const { return float_value(OPTION_CONTRAST); }
/*TODO*///	float gamma() const { return float_value(OPTION_GAMMA); }
/*TODO*///	float pause_brightness() const { return float_value(OPTION_PAUSE_BRIGHTNESS); }
/*TODO*///	const char *effect() const { return value(OPTION_EFFECT); }
/*TODO*///
/*TODO*///	// core vector options
/*TODO*///	float beam_width_min() const { return float_value(OPTION_BEAM_WIDTH_MIN); }
/*TODO*///	float beam_width_max() const { return float_value(OPTION_BEAM_WIDTH_MAX); }
/*TODO*///	float beam_dot_size() const { return float_value(OPTION_BEAM_DOT_SIZE); }
/*TODO*///	float beam_intensity_weight() const { return float_value(OPTION_BEAM_INTENSITY_WEIGHT); }
/*TODO*///	float flicker() const { return float_value(OPTION_FLICKER); }
/*TODO*///
/*TODO*///	// core sound options
/*TODO*///	int sample_rate() const { return int_value(OPTION_SAMPLERATE); }
/*TODO*///	bool samples() const { return bool_value(OPTION_SAMPLES); }
/*TODO*///	int volume() const { return int_value(OPTION_VOLUME); }
/*TODO*///	int speaker_report() const { return int_value(OPTION_SPEAKER_REPORT); }
/*TODO*///
/*TODO*///	// core input options
/*TODO*///	bool coin_lockout() const { return bool_value(OPTION_COIN_LOCKOUT); }
/*TODO*///	const char *ctrlr() const { return value(OPTION_CTRLR); }
/*TODO*///	bool mouse() const { return bool_value(OPTION_MOUSE); }
/*TODO*///	bool joystick() const { return bool_value(OPTION_JOYSTICK); }
/*TODO*///	bool lightgun() const { return bool_value(OPTION_LIGHTGUN); }
/*TODO*///	bool multi_keyboard() const { return bool_value(OPTION_MULTIKEYBOARD); }
/*TODO*///	bool multi_mouse() const { return bool_value(OPTION_MULTIMOUSE); }
/*TODO*///	const char *paddle_device() const { return value(OPTION_PADDLE_DEVICE); }
/*TODO*///	const char *adstick_device() const { return value(OPTION_ADSTICK_DEVICE); }
/*TODO*///	const char *pedal_device() const { return value(OPTION_PEDAL_DEVICE); }
/*TODO*///	const char *dial_device() const { return value(OPTION_DIAL_DEVICE); }
/*TODO*///	const char *trackball_device() const { return value(OPTION_TRACKBALL_DEVICE); }
/*TODO*///	const char *lightgun_device() const { return value(OPTION_LIGHTGUN_DEVICE); }
/*TODO*///	const char *positional_device() const { return value(OPTION_POSITIONAL_DEVICE); }
/*TODO*///	const char *mouse_device() const { return value(OPTION_MOUSE_DEVICE); }
/*TODO*///	const char *joystick_map() const { return value(OPTION_JOYSTICK_MAP); }
/*TODO*///	float joystick_deadzone() const { return float_value(OPTION_JOYSTICK_DEADZONE); }
/*TODO*///	float joystick_saturation() const { return float_value(OPTION_JOYSTICK_SATURATION); }
/*TODO*///	bool steadykey() const { return bool_value(OPTION_STEADYKEY); }
/*TODO*///	bool ui_active() const { return bool_value(OPTION_UI_ACTIVE); }
/*TODO*///	bool offscreen_reload() const { return bool_value(OPTION_OFFSCREEN_RELOAD); }
/*TODO*///	bool natural_keyboard() const { return bool_value(OPTION_NATURAL_KEYBOARD); }
/*TODO*///	bool joystick_contradictory() const { return m_joystick_contradictory; }
/*TODO*///	int coin_impulse() const { return m_coin_impulse; }
/*TODO*///
/*TODO*///	// core debugging options
/*TODO*///	bool log() const { return bool_value(OPTION_LOG); }
/*TODO*///	bool debug() const { return bool_value(OPTION_DEBUG); }
/*TODO*///	bool verbose() const { return bool_value(OPTION_VERBOSE); }
/*TODO*///	bool oslog() const { return bool_value(OPTION_OSLOG); }
/*TODO*///	const char *debug_script() const { return value(OPTION_DEBUGSCRIPT); }
/*TODO*///	bool update_in_pause() const { return bool_value(OPTION_UPDATEINPAUSE); }
/*TODO*///	bool debuglog() const { return bool_value(OPTION_DEBUGLOG); }
/*TODO*///
/*TODO*///	// core misc options
/*TODO*///	bool drc() const { return bool_value(OPTION_DRC); }
/*TODO*///	bool drc_use_c() const { return bool_value(OPTION_DRC_USE_C); }
/*TODO*///	bool drc_log_uml() const { return bool_value(OPTION_DRC_LOG_UML); }
/*TODO*///	bool drc_log_native() const { return bool_value(OPTION_DRC_LOG_NATIVE); }
/*TODO*///	const char *bios() const { return value(OPTION_BIOS); }
/*TODO*///	bool cheat() const { return bool_value(OPTION_CHEAT); }
/*TODO*///	bool skip_gameinfo() const { return bool_value(OPTION_SKIP_GAMEINFO); }
/*TODO*///	const char *ui_font() const { return value(OPTION_UI_FONT); }
/*TODO*///	ui_option ui() const { return m_ui; }
/*TODO*///	const char *ram_size() const { return value(OPTION_RAMSIZE); }
/*TODO*///	bool nvram_save() const { return bool_value(OPTION_NVRAM_SAVE); }
/*TODO*///
/*TODO*///	// core comm options
/*TODO*///	const char *comm_localhost() const { return value(OPTION_COMM_LOCAL_HOST); }
/*TODO*///	const char *comm_localport() const { return value(OPTION_COMM_LOCAL_PORT); }
/*TODO*///	const char *comm_remotehost() const { return value(OPTION_COMM_REMOTE_HOST); }
/*TODO*///	const char *comm_remoteport() const { return value(OPTION_COMM_REMOTE_PORT); }
/*TODO*///	bool comm_framesync() const { return bool_value(OPTION_COMM_FRAME_SYNC); }
/*TODO*///
/*TODO*///
/*TODO*///	bool confirm_quit() const { return bool_value(OPTION_CONFIRM_QUIT); }
/*TODO*///	bool ui_mouse() const { return bool_value(OPTION_UI_MOUSE); }
/*TODO*///
/*TODO*///	const char *autoboot_command() const { return value(OPTION_AUTOBOOT_COMMAND); }
/*TODO*///	int autoboot_delay() const { return int_value(OPTION_AUTOBOOT_DELAY); }
/*TODO*///	const char *autoboot_script() const { return value(OPTION_AUTOBOOT_SCRIPT); }
/*TODO*///
/*TODO*///	bool console() const { return bool_value(OPTION_CONSOLE); }
/*TODO*///
/*TODO*///	bool plugins() const { return bool_value(OPTION_PLUGINS); }
/*TODO*///
/*TODO*///	const char *plugin() const { return value(OPTION_PLUGIN); }
/*TODO*///	const char *no_plugin() const { return value(OPTION_NO_PLUGIN); }
/*TODO*///
/*TODO*///	const char *language() const { return value(OPTION_LANGUAGE); }
/*TODO*///
/*TODO*///	// Web server specific options
/*TODO*///	bool  http() const { return bool_value(OPTION_HTTP); }
/*TODO*///	short http_port() const { return int_value(OPTION_HTTP_PORT); }
/*TODO*///	const char *http_root() const { return value(OPTION_HTTP_ROOT); }
/*TODO*///
/*TODO*///	// slots and devices - the values for these are stored outside of the core_options
/*TODO*///	// structure
/*TODO*///	const ::slot_option &slot_option(const std::string &device_name) const;
/*TODO*///	::slot_option &slot_option(const std::string &device_name);
/*TODO*///	const ::slot_option *find_slot_option(const std::string &device_name) const;
/*TODO*///	::slot_option *find_slot_option(const std::string &device_name);
/*TODO*///	bool has_slot_option(const std::string &device_name) const { return find_slot_option(device_name) ? true : false; }
/*TODO*///	const ::image_option &image_option(const std::string &device_name) const;
/*TODO*///	::image_option &image_option(const std::string &device_name);
/*TODO*///	bool has_image_option(const std::string &device_name) const { return m_image_options.find(device_name) != m_image_options.end(); }
/*TODO*///
/*TODO*///protected:
/*TODO*///	virtual void command_argument_processed() override;
/*TODO*///
/*TODO*///private:
/*TODO*///	struct software_options
/*TODO*///	{
/*TODO*///		std::unordered_map<std::string, std::string>    slot;
/*TODO*///		std::unordered_map<std::string, std::string>    image;
/*TODO*///	};
/*TODO*///
/*TODO*///	// slot/image/softlist calculus
/*TODO*///	software_options evaluate_initial_softlist_options(const std::string &software_identifier);
/*TODO*///	void update_slot_and_image_options();
/*TODO*///	bool add_and_remove_slot_options();
/*TODO*///	bool add_and_remove_image_options();
/*TODO*///	void reevaluate_default_card_software();
/*TODO*///	std::string get_default_card_software(device_slot_interface &slot);
/*TODO*///
/*TODO*///	// static list of options entries
/*TODO*///	static const options_entry                          s_option_entries[];
/*TODO*///
/*TODO*///	// the basics
/*TODO*///	option_support                                      m_support;
/*TODO*///	const game_driver *                                 m_system;
/*TODO*///
/*TODO*///	// slots and devices
/*TODO*///	std::unordered_map<std::string, ::slot_option>      m_slot_options;
/*TODO*///	std::unordered_map<std::string, ::image_option>     m_image_options_canonical;
/*TODO*///	std::unordered_map<std::string, ::image_option *>   m_image_options;
/*TODO*///
/*TODO*///	// cached options, for scenarios where parsing core_options is too slow
/*TODO*///	int                                                 m_coin_impulse;
/*TODO*///	bool                                                m_joystick_contradictory;
/*TODO*///	bool                                                m_sleep;
/*TODO*///	bool                                                m_refresh_speed;
/*TODO*///	ui_option                                           m_ui;
/*TODO*///
/*TODO*///	// special option; the system name we tried to specify
/*TODO*///	std::string                                         m_attempted_system_name;
/*TODO*///
/*TODO*///	// special option; the software set name that we did specify
/*TODO*///	std::string                                         m_software_name;
/*TODO*///};
/*TODO*///
/*TODO*///// takes an existing emu_options and adds system specific options
/*TODO*///void osd_setup_osd_specific_emu_options(emu_options &opts);
/*TODO*///
/*TODO*///#endif  // MAME_EMU_EMUOPTS_H
    
}
