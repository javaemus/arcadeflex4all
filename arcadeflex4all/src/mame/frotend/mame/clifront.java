// license:BSD-3-Clause
// copyright-holders:Aaron Giles
/***************************************************************************

    clifront.cpp

    Command-line interface frontend for MAME.

***************************************************************************/

package mame.frotend.mame;

public class clifront {

/*TODO*///#include "emu.h"
/*TODO*///#include "clifront.h"
/*TODO*///
/*TODO*///#include "ui/moptions.h"
/*TODO*///
/*TODO*///#include "audit.h"
/*TODO*///#include "infoxml.h"
/*TODO*///#include "language.h"
/*TODO*///#include "luaengine.h"
/*TODO*///#include "mame.h"
/*TODO*///#include "media_ident.h"
/*TODO*///#include "pluginopts.h"
/*TODO*///
/*TODO*///#include "emuopts.h"
/*TODO*///#include "mameopts.h"
/*TODO*///#include "romload.h"
/*TODO*///#include "softlist_dev.h"
/*TODO*///#include "validity.h"
/*TODO*///#include "sound/samples.h"
/*TODO*///
/*TODO*///#include "chd.h"
/*TODO*///#include "unzip.h"
/*TODO*///#include "xmlfile.h"
/*TODO*///
/*TODO*///#include "osdepend.h"
/*TODO*///
/*TODO*///#include <algorithm>
/*TODO*///#include <new>
/*TODO*///#include <cctype>


    //**************************************************************************
    //  CONSTANTS
    //**************************************************************************

    // core commands
    public static final String CLICOMMAND_HELP                 = "help";
    public static final String CLICOMMAND_VALIDATE             = "validate";

    // configuration commands
    public static final String CLICOMMAND_CREATECONFIG         = "createconfig";
    public static final String CLICOMMAND_SHOWCONFIG           = "showconfig";
    public static final String CLICOMMAND_SHOWUSAGE            = "showusage";

    // frontend commands
    public static final String CLICOMMAND_LISTXML              = "listxml";
    public static final String CLICOMMAND_LISTFULL             = "listfull";
    public static final String CLICOMMAND_LISTSOURCE           = "listsource";
    public static final String CLICOMMAND_LISTCLONES           = "listclones";
    public static final String CLICOMMAND_LISTBROTHERS         = "listbrothers";
    public static final String CLICOMMAND_LISTCRC              = "listcrc";
    public static final String CLICOMMAND_LISTROMS             = "listroms";
    public static final String CLICOMMAND_LISTSAMPLES          = "listsamples";
    public static final String CLICOMMAND_VERIFYROMS           = "verifyroms";
    public static final String CLICOMMAND_VERIFYSAMPLES        = "verifysamples";
    public static final String CLICOMMAND_ROMIDENT             = "romident";
    public static final String CLICOMMAND_LISTDEVICES          = "listdevices";
    public static final String CLICOMMAND_LISTSLOTS            = "listslots";
    public static final String CLICOMMAND_LISTMEDIA            = "listmedia";
    public static final String CLICOMMAND_LISTSOFTWARE         = "listsoftware";
    public static final String CLICOMMAND_VERIFYSOFTWARE       = "verifysoftware";
    public static final String CLICOMMAND_GETSOFTLIST          = "getsoftlist";
    public static final String CLICOMMAND_VERIFYSOFTLIST       = "verifysoftlist";
    public static final String CLICOMMAND_VERSION              = "version";

    // command options
    public static final String CLIOPTION_DTD                   = "dtd";


/*TODO*///namespace {
/*TODO*/////**************************************************************************
/*TODO*/////  COMMAND-LINE OPTIONS
/*TODO*/////**************************************************************************
/*TODO*///
/*TODO*///const options_entry cli_option_entries[] =
/*TODO*///{
/*TODO*///	/* core commands */
/*TODO*///	{ nullptr,                              nullptr,   OPTION_HEADER,     "CORE COMMANDS" },
/*TODO*///	{ CLICOMMAND_HELP           ";h;?",     "0",       OPTION_COMMAND,    "show help message" },
/*TODO*///	{ CLICOMMAND_VALIDATE       ";valid",   "0",       OPTION_COMMAND,    "perform validation on system drivers and devices" },
/*TODO*///
/*TODO*///	/* configuration commands */
/*TODO*///	{ nullptr,                              nullptr,   OPTION_HEADER,     "CONFIGURATION COMMANDS" },
/*TODO*///	{ CLICOMMAND_CREATECONFIG   ";cc",      "0",       OPTION_COMMAND,    "create the default configuration file" },
/*TODO*///	{ CLICOMMAND_SHOWCONFIG     ";sc",      "0",       OPTION_COMMAND,    "display running parameters" },
/*TODO*///	{ CLICOMMAND_SHOWUSAGE      ";su",      "0",       OPTION_COMMAND,    "show this help" },
/*TODO*///
/*TODO*///	/* frontend commands */
/*TODO*///	{ nullptr,                              nullptr,   OPTION_HEADER,     "FRONTEND COMMANDS" },
/*TODO*///	{ CLICOMMAND_LISTXML        ";lx",      "0",       OPTION_COMMAND,    "all available info on driver in XML format" },
/*TODO*///	{ CLICOMMAND_LISTFULL       ";ll",      "0",       OPTION_COMMAND,    "short name, full name" },
/*TODO*///	{ CLICOMMAND_LISTSOURCE     ";ls",      "0",       OPTION_COMMAND,    "driver sourcefile" },
/*TODO*///	{ CLICOMMAND_LISTCLONES     ";lc",      "0",       OPTION_COMMAND,    "show clones" },
/*TODO*///	{ CLICOMMAND_LISTBROTHERS   ";lb",      "0",       OPTION_COMMAND,    "show \"brothers\", or other drivers from same sourcefile" },
/*TODO*///	{ CLICOMMAND_LISTCRC,                   "0",       OPTION_COMMAND,    "CRC-32s" },
/*TODO*///	{ CLICOMMAND_LISTROMS       ";lr",      "0",       OPTION_COMMAND,    "list required ROMs for a driver" },
/*TODO*///	{ CLICOMMAND_LISTSAMPLES,               "0",       OPTION_COMMAND,    "list optional samples for a driver" },
/*TODO*///	{ CLICOMMAND_VERIFYROMS,                "0",       OPTION_COMMAND,    "report romsets that have problems" },
/*TODO*///	{ CLICOMMAND_VERIFYSAMPLES,             "0",       OPTION_COMMAND,    "report samplesets that have problems" },
/*TODO*///	{ CLICOMMAND_ROMIDENT,                  "0",       OPTION_COMMAND,    "compare files with known MAME ROMs" },
/*TODO*///	{ CLICOMMAND_LISTDEVICES    ";ld",      "0",       OPTION_COMMAND,    "list available devices" },
/*TODO*///	{ CLICOMMAND_LISTSLOTS      ";lslot",   "0",       OPTION_COMMAND,    "list available slots and slot devices" },
/*TODO*///	{ CLICOMMAND_LISTMEDIA      ";lm",      "0",       OPTION_COMMAND,    "list available media for the system" },
/*TODO*///	{ CLICOMMAND_LISTSOFTWARE   ";lsoft",   "0",       OPTION_COMMAND,    "list known software for the system" },
/*TODO*///	{ CLICOMMAND_VERIFYSOFTWARE ";vsoft",   "0",       OPTION_COMMAND,    "verify known software for the system" },
/*TODO*///	{ CLICOMMAND_GETSOFTLIST    ";glist",   "0",       OPTION_COMMAND,    "retrieve software list by name" },
/*TODO*///	{ CLICOMMAND_VERIFYSOFTLIST ";vlist",   "0",       OPTION_COMMAND,    "verify software list by name" },
/*TODO*///	{ CLICOMMAND_VERSION,                   "0",       OPTION_COMMAND,    "get MAME version" },
/*TODO*///
/*TODO*///	{ nullptr,                              nullptr,   OPTION_HEADER,     "FRONTEND COMMAND OPTIONS" },
/*TODO*///	{ CLIOPTION_DTD,                        "1",       OPTION_BOOLEAN,    "include DTD in XML output" },
/*TODO*///	{ nullptr }
/*TODO*///};
/*TODO*///
/*TODO*///
/*TODO*///void print_summary(
/*TODO*///		const media_auditor &auditor, media_auditor::summary summary, bool record_none_needed,
/*TODO*///		const char *type, const char *name, const char *parent,
/*TODO*///		unsigned &correct, unsigned &incorrect, unsigned &notfound,
/*TODO*///		util::ovectorstream &buffer)
/*TODO*///{
/*TODO*///	if (summary == media_auditor::NOTFOUND)
/*TODO*///	{
/*TODO*///		// if not found, count that and leave it at that
/*TODO*///		++notfound;
/*TODO*///	}
/*TODO*///	else if (record_none_needed || (summary != media_auditor::NONE_NEEDED))
/*TODO*///	{
/*TODO*///		// output the summary of the audit
/*TODO*///		buffer.clear();
/*TODO*///		buffer.seekp(0);
/*TODO*///		auditor.summarize(name, &buffer);
/*TODO*///		buffer.put('\0');
/*TODO*///		osd_printf_info("%s", &buffer.vec()[0]);
/*TODO*///
/*TODO*///		// output the name of the driver and its parent
/*TODO*///		osd_printf_info("%sset %s ", type, name);
/*TODO*///		if (parent)
/*TODO*///			osd_printf_info("[%s] ", parent);
/*TODO*///
/*TODO*///		// switch off of the result
/*TODO*///		switch (summary)
/*TODO*///		{
/*TODO*///		case media_auditor::INCORRECT:
/*TODO*///			osd_printf_info("is bad\n");
/*TODO*///			++incorrect;
/*TODO*///			return;
/*TODO*///
/*TODO*///		case media_auditor::CORRECT:
/*TODO*///			osd_printf_info("is good\n");
/*TODO*///			++correct;
/*TODO*///			return;
/*TODO*///
/*TODO*///		case media_auditor::BEST_AVAILABLE:
/*TODO*///		case media_auditor::NONE_NEEDED:
/*TODO*///			osd_printf_info("is best available\n");
/*TODO*///			++correct;
/*TODO*///			return;
/*TODO*///
/*TODO*///		case media_auditor::NOTFOUND:
/*TODO*///			osd_printf_info("not found\n");
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		assert(false);
/*TODO*///		osd_printf_error("has unknown status (%u)\n", unsigned(summary));
/*TODO*///	}
/*TODO*///}
/*TODO*///
/*TODO*///} // anonymous namespace
/*TODO*///
/*TODO*///
/*TODO*/////**************************************************************************
/*TODO*/////  CLI FRONTEND
/*TODO*/////**************************************************************************
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  cli_frontend - constructor
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///cli_frontend::cli_frontend(emu_options &options, osd_interface &osd)
/*TODO*///	: m_options(options)
/*TODO*///	, m_osd(osd)
/*TODO*///	, m_result(EMU_ERR_NONE)
/*TODO*///{
/*TODO*///	m_options.add_entries(cli_option_entries);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  ~cli_frontend - destructor
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///cli_frontend::~cli_frontend()
/*TODO*///{
/*TODO*///}
/*TODO*///
/*TODO*///void cli_frontend::start_execution(mame_machine_manager *manager, const std::vector<std::string> &args)
/*TODO*///{
/*TODO*///	std::ostringstream option_errors;
/*TODO*///
/*TODO*///	// because softlist evaluation relies on hashpath being populated, we are going to go through
/*TODO*///	// a special step to force it to be evaluated
/*TODO*///	mame_options::populate_hashpath_from_args_and_inis(m_options, args);
/*TODO*///
/*TODO*///	// parse the command line, adding any system-specific options
/*TODO*///	try
/*TODO*///	{
/*TODO*///		m_options.parse_command_line(args, OPTION_PRIORITY_CMDLINE);
/*TODO*///		m_osd.set_verbose(m_options.verbose());
/*TODO*///	}
/*TODO*///	catch (options_exception &ex)
/*TODO*///	{
/*TODO*///		// if we failed, check for no command and a system name first; in that case error on the name
/*TODO*///		if (m_options.command().empty() && mame_options::system(m_options) == nullptr && !m_options.attempted_system_name().empty())
/*TODO*///			throw emu_fatalerror(EMU_ERR_NO_SUCH_SYSTEM, "Unknown system '%s'", m_options.attempted_system_name().c_str());
/*TODO*///
/*TODO*///		// otherwise, error on the options
/*TODO*///		throw emu_fatalerror(EMU_ERR_INVALID_CONFIG, "%s", ex.message().c_str());
/*TODO*///	}
/*TODO*///
/*TODO*///	// determine the base name of the EXE
/*TODO*///	std::string exename = core_filename_extract_base(args[0], true);
/*TODO*///
/*TODO*///	// if we have a command, execute that
/*TODO*///	if (!m_options.command().empty())
/*TODO*///	{
/*TODO*///		execute_commands(exename.c_str());
/*TODO*///		return;
/*TODO*///	}
/*TODO*///
/*TODO*///	// read INI's, if appropriate
/*TODO*///	if (m_options.read_config())
/*TODO*///	{
/*TODO*///		mame_options::parse_standard_inis(m_options, option_errors);
/*TODO*///		m_osd.set_verbose(m_options.verbose());
/*TODO*///	}
/*TODO*///
/*TODO*///	// otherwise, check for a valid system
/*TODO*///	load_translation(m_options);
/*TODO*///
/*TODO*///	manager->start_http_server();
/*TODO*///
/*TODO*///	manager->start_luaengine();
/*TODO*///
/*TODO*///	if (option_errors.tellp() > 0)
/*TODO*///	{
/*TODO*///		std::string option_errors_string = option_errors.str();
/*TODO*///		osd_printf_error("Error in command line:\n%s\n", strtrimspace(option_errors_string));
/*TODO*///	}
/*TODO*///
/*TODO*///	// if we can't find it, give an appropriate error
/*TODO*///	const game_driver *system = mame_options::system(m_options);
/*TODO*///	if (system == nullptr && *(m_options.system_name()) != 0)
/*TODO*///		throw emu_fatalerror(EMU_ERR_NO_SUCH_SYSTEM, "Unknown system '%s'", m_options.system_name());
/*TODO*///
/*TODO*///	// otherwise just run the game
/*TODO*///	m_result = manager->execute();
/*TODO*///}
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  execute - execute a game via the standard
/*TODO*/////  command line interface
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///int cli_frontend::execute(std::vector<std::string> &args)
/*TODO*///{
/*TODO*///	// wrap the core execution in a try/catch to field all fatal errors
/*TODO*///	m_result = EMU_ERR_NONE;
/*TODO*///	mame_machine_manager *manager = mame_machine_manager::instance(m_options, m_osd);
/*TODO*///
/*TODO*///	try
/*TODO*///	{
/*TODO*///		start_execution(manager, args);
/*TODO*///	}
/*TODO*///	// handle exceptions of various types
/*TODO*///	catch (emu_fatalerror &fatal)
/*TODO*///	{
/*TODO*///		std::string str(fatal.what());
/*TODO*///		strtrimspace(str);
/*TODO*///		osd_printf_error("%s\n", str);
/*TODO*///		m_result = (fatal.exitcode() != 0) ? fatal.exitcode() : EMU_ERR_FATALERROR;
/*TODO*///
/*TODO*///		// if a game was specified, wasn't a wildcard, and our error indicates this was the
/*TODO*///		// reason for failure, offer some suggestions
/*TODO*///		if (m_result == EMU_ERR_NO_SUCH_SYSTEM
/*TODO*///			&& !m_options.attempted_system_name().empty()
/*TODO*///			&& !core_iswildstr(m_options.attempted_system_name().c_str())
/*TODO*///			&& mame_options::system(m_options) == nullptr)
/*TODO*///		{
/*TODO*///			// get the top 16 approximate matches
/*TODO*///			driver_enumerator drivlist(m_options);
/*TODO*///			int matches[16];
/*TODO*///			drivlist.find_approximate_matches(m_options.attempted_system_name(), ARRAY_LENGTH(matches), matches);
/*TODO*///
/*TODO*///			// work out how wide the titles need to be
/*TODO*///			int titlelen(0);
/*TODO*///			for (int match : matches)
/*TODO*///				if (0 <= match)
/*TODO*///					titlelen = (std::max)(titlelen, int(strlen(drivlist.driver(match).type.fullname())));
/*TODO*///
/*TODO*///			// print them out
/*TODO*///			osd_printf_error("\n\"%s\" approximately matches the following\n"
/*TODO*///					"supported machines (best match first):\n\n", m_options.attempted_system_name().c_str());
/*TODO*///			for (int match : matches)
/*TODO*///			{
/*TODO*///				if (0 <= match)
/*TODO*///				{
/*TODO*///					game_driver const &drv(drivlist.driver(match));
/*TODO*///					osd_printf_error("%-18s%-*s(%s, %s)\n", drv.name, titlelen + 2, drv.type.fullname(), drv.manufacturer, drv.year);
/*TODO*///				}
/*TODO*///			}
/*TODO*///		}
/*TODO*///	}
/*TODO*///	catch (emu_exception &)
/*TODO*///	{
/*TODO*///		osd_printf_error("Caught unhandled emulator exception\n");
/*TODO*///		m_result = EMU_ERR_FATALERROR;
/*TODO*///	}
/*TODO*///	catch (tag_add_exception &aex)
/*TODO*///	{
/*TODO*///		osd_printf_error("Tag '%s' already exists in tagged map\n", aex.tag());
/*TODO*///		m_result = EMU_ERR_FATALERROR;
/*TODO*///	}
/*TODO*///	catch (std::exception &ex)
/*TODO*///	{
/*TODO*///		osd_printf_error("Caught unhandled %s exception: %s\n", typeid(ex).name(), ex.what());
/*TODO*///		m_result = EMU_ERR_FATALERROR;
/*TODO*///	}
/*TODO*///	catch (...)
/*TODO*///	{
/*TODO*///		osd_printf_error("Caught unhandled exception\n");
/*TODO*///		m_result = EMU_ERR_FATALERROR;
/*TODO*///	}
/*TODO*///
/*TODO*///	util::archive_file::cache_clear();
/*TODO*///	global_free(manager);
/*TODO*///
/*TODO*///	return m_result;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  listxml - output the XML data for one or more
/*TODO*/////  games
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///void cli_frontend::listxml(const std::vector<std::string> &args)
/*TODO*///{
/*TODO*///	// create the XML and print it to stdout
/*TODO*///	info_xml_creator creator(m_options, m_options.bool_value(CLIOPTION_DTD));
/*TODO*///	creator.output(std::cout, args);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  listfull - output the name and description of
/*TODO*/////  one or more games
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///void cli_frontend::listfull(const std::vector<std::string> &args)
/*TODO*///{
/*TODO*///	auto const list_system_name = [] (device_type type, bool first)
/*TODO*///	{
/*TODO*///		// print the header
/*TODO*///		if (first)
/*TODO*///			osd_printf_info("Name:             Description:\n");
/*TODO*///
/*TODO*///		osd_printf_info("%-17s \"%s\"\n", type.shortname(), type.fullname());
/*TODO*///	};
/*TODO*///	apply_action(
/*TODO*///			args,
/*TODO*///			[&list_system_name] (driver_enumerator &drivlist, bool first)
/*TODO*///			{ list_system_name(drivlist.driver().type, first); },
/*TODO*///			[&list_system_name] (device_type type, bool first)
/*TODO*///			{ list_system_name(type, first); });
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  listsource - output the name and source
/*TODO*/////  filename of one or more games
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///void cli_frontend::listsource(const std::vector<std::string> &args)
/*TODO*///{
/*TODO*///	auto const list_system_source = [] (device_type type)
/*TODO*///	{
/*TODO*///		osd_printf_info("%-16s %s\n", type.shortname(), core_filename_extract_base(type.source()));
/*TODO*///	};
/*TODO*///	apply_action(
/*TODO*///			args,
/*TODO*///			[&list_system_source] (driver_enumerator &drivlist, bool first)
/*TODO*///			{ list_system_source(drivlist.driver().type); },
/*TODO*///			[&list_system_source] (device_type type, bool first)
/*TODO*///			{ list_system_source(type); });
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  listclones - output the name and parent of all
/*TODO*/////  clones matching the given pattern
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///void cli_frontend::listclones(const std::vector<std::string> &args)
/*TODO*///{
/*TODO*///	const char *gamename = args.empty() ? nullptr : args[0].c_str();
/*TODO*///
/*TODO*///	// start with a filtered list of drivers
/*TODO*///	driver_enumerator drivlist(m_options, gamename);
/*TODO*///	int const original_count = drivlist.count();
/*TODO*///
/*TODO*///	// iterate through the remaining ones to see if their parent matches
/*TODO*///	while (drivlist.next_excluded())
/*TODO*///	{
/*TODO*///		// if we have a non-bios clone and it matches, keep it
/*TODO*///		int const clone_of = drivlist.clone();
/*TODO*///		if ((clone_of >= 0) && !(drivlist.driver(clone_of).flags & machine_flags::IS_BIOS_ROOT))
/*TODO*///			if (drivlist.matches(gamename, drivlist.driver(clone_of).name))
/*TODO*///				drivlist.include();
/*TODO*///	}
/*TODO*///
/*TODO*///	// return an error if none found
/*TODO*///	if (drivlist.count() == 0)
/*TODO*///	{
/*TODO*///		// see if we match but just weren't a clone
/*TODO*///		if (original_count == 0)
/*TODO*///			throw emu_fatalerror(EMU_ERR_NO_SUCH_SYSTEM, "No matching systems found for '%s'", gamename);
/*TODO*///		else
/*TODO*///			osd_printf_info("Found %lu match(es) for '%s' but none were clones\n", (unsigned long)drivlist.count(), gamename); // FIXME: this never gets hit
/*TODO*///		return;
/*TODO*///	}
/*TODO*///
/*TODO*///	// print the header
/*TODO*///	osd_printf_info("Name:            Clone of:\n");
/*TODO*///
/*TODO*///	// iterate through drivers and output the info
/*TODO*///	drivlist.reset();
/*TODO*///	while (drivlist.next())
/*TODO*///	{
/*TODO*///		int clone_of = drivlist.clone();
/*TODO*///		if ((clone_of >= 0) && !(drivlist.driver(clone_of).flags & machine_flags::IS_BIOS_ROOT))
/*TODO*///			osd_printf_info("%-16s %s\n", drivlist.driver().name, drivlist.driver(clone_of).name);
/*TODO*///	}
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  listbrothers - for each matching game, output
/*TODO*/////  the list of other games that share the same
/*TODO*/////  source file
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///void cli_frontend::listbrothers(const std::vector<std::string> &args)
/*TODO*///{
/*TODO*///	const char *gamename = args.empty() ? nullptr : args[0].c_str();
/*TODO*///
/*TODO*///	// start with a filtered list of drivers; return an error if none found
/*TODO*///	driver_enumerator initial_drivlist(m_options, gamename);
/*TODO*///	if (initial_drivlist.count() == 0)
/*TODO*///		throw emu_fatalerror(EMU_ERR_NO_SUCH_SYSTEM, "No matching systems found for '%s'", gamename);
/*TODO*///
/*TODO*///	// for the final list, start with an empty driver list
/*TODO*///	driver_enumerator drivlist(m_options);
/*TODO*///	drivlist.exclude_all();
/*TODO*///
/*TODO*///	// scan through the initially-selected drivers
/*TODO*///	while (initial_drivlist.next())
/*TODO*///	{
/*TODO*///		// if we are already marked in the final list, we don't need to do anything
/*TODO*///		if (drivlist.included(initial_drivlist.current()))
/*TODO*///			continue;
/*TODO*///
/*TODO*///		// otherwise, walk excluded items in the final list and mark any that match
/*TODO*///		drivlist.reset();
/*TODO*///		while (drivlist.next_excluded())
/*TODO*///			if (strcmp(drivlist.driver().type.source(), initial_drivlist.driver().type.source()) == 0)
/*TODO*///				drivlist.include();
/*TODO*///	}
/*TODO*///
/*TODO*///	// print the header
/*TODO*///	osd_printf_info("%-20s %-16s %s\n", "Source file:", "Name:", "Parent:");
/*TODO*///
/*TODO*///	// output the entries found
/*TODO*///	drivlist.reset();
/*TODO*///	while (drivlist.next())
/*TODO*///	{
/*TODO*///		int clone_of = drivlist.clone();
/*TODO*///		if (clone_of != -1)
/*TODO*///			osd_printf_info("%-20s %-16s %s\n", core_filename_extract_base(drivlist.driver().type.source()), drivlist.driver().name, (clone_of == -1 ? "" : drivlist.driver(clone_of).name));
/*TODO*///		else
/*TODO*///			osd_printf_info("%-20s %s\n", core_filename_extract_base(drivlist.driver().type.source()), drivlist.driver().name);
/*TODO*///	}
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  listcrc - output the CRC and name of all ROMs
/*TODO*/////  referenced by the emulator
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///void cli_frontend::listcrc(const std::vector<std::string> &args)
/*TODO*///{
/*TODO*///	apply_device_action(
/*TODO*///			args,
/*TODO*///			[] (device_t &root, char const *type, bool first)
/*TODO*///			{
/*TODO*///				for (device_t const &device : device_iterator(root))
/*TODO*///				{
/*TODO*///					for (tiny_rom_entry const *rom = device.rom_region(); rom && !ROMENTRY_ISEND(rom); ++rom)
/*TODO*///					{
/*TODO*///						if (ROMENTRY_ISFILE(rom))
/*TODO*///						{
/*TODO*///							// if we have a CRC, display it
/*TODO*///							uint32_t crc;
/*TODO*///							if (util::hash_collection(rom->hashdata).crc(crc))
/*TODO*///								osd_printf_info("%08x %-32s\t%-16s\t%s\n", crc, rom->name, device.shortname(), device.name());
/*TODO*///						}
/*TODO*///					}
/*TODO*///				}
/*TODO*///			});
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  listroms - output the list of ROMs referenced
/*TODO*/////  by matching systems/devices
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///void cli_frontend::listroms(const std::vector<std::string> &args)
/*TODO*///{
/*TODO*///	apply_device_action(
/*TODO*///			args,
/*TODO*///			[] (device_t &root, char const *type, bool first)
/*TODO*///			{
/*TODO*///				// space between items
/*TODO*///				if (!first)
/*TODO*///					osd_printf_info("\n");
/*TODO*///
/*TODO*///				// iterate through ROMs
/*TODO*///				bool hasroms = false;
/*TODO*///				for (device_t const &device : device_iterator(root))
/*TODO*///				{
/*TODO*///					for (const rom_entry *region = rom_first_region(device); region; region = rom_next_region(region))
/*TODO*///					{
/*TODO*///						for (const rom_entry *rom = rom_first_file(region); rom; rom = rom_next_file(rom))
/*TODO*///						{
/*TODO*///							// print a header
/*TODO*///							if (!hasroms)
/*TODO*///								osd_printf_info(
/*TODO*///									"ROMs required for %s \"%s\".\n"
/*TODO*///									"%-32s %10s %s\n",
/*TODO*///									type, root.shortname(), "Name", "Size", "Checksum");
/*TODO*///							hasroms = true;
/*TODO*///
/*TODO*///							// accumulate the total length of all chunks
/*TODO*///							int64_t length = -1;
/*TODO*///							if (ROMREGION_ISROMDATA(region))
/*TODO*///								length = rom_file_size(rom);
/*TODO*///
/*TODO*///							// start with the name
/*TODO*///							const char *name = ROM_GETNAME(rom);
/*TODO*///							osd_printf_info("%-32s ", name);
/*TODO*///
/*TODO*///							// output the length next
/*TODO*///							if (length >= 0)
/*TODO*///								osd_printf_info("%10u", unsigned(uint64_t(length)));
/*TODO*///							else
/*TODO*///								osd_printf_info("%10s", "");
/*TODO*///
/*TODO*///							// output the hash data
/*TODO*///							util::hash_collection hashes(ROM_GETHASHDATA(rom));
/*TODO*///							if (!hashes.flag(util::hash_collection::FLAG_NO_DUMP))
/*TODO*///							{
/*TODO*///								if (hashes.flag(util::hash_collection::FLAG_BAD_DUMP))
/*TODO*///									osd_printf_info(" BAD");
/*TODO*///								osd_printf_info(" %s", hashes.macro_string());
/*TODO*///							}
/*TODO*///							else
/*TODO*///								osd_printf_info(" NO GOOD DUMP KNOWN");
/*TODO*///
/*TODO*///							// end with a CR
/*TODO*///							osd_printf_info("\n");
/*TODO*///						}
/*TODO*///					}
/*TODO*///				}
/*TODO*///				if (!hasroms)
/*TODO*///					osd_printf_info("No ROMs required for %s \"%s\".\n", type, root.shortname());
/*TODO*///			});
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  listsamples - output the list of samples
/*TODO*/////  referenced by a given game or set of games
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///void cli_frontend::listsamples(const std::vector<std::string> &args)
/*TODO*///{
/*TODO*///	const char *gamename = args.empty() ? nullptr : args[0].c_str();
/*TODO*///
/*TODO*///	// determine which drivers to output; return an error if none found
/*TODO*///	driver_enumerator drivlist(m_options, gamename);
/*TODO*///	if (drivlist.count() == 0)
/*TODO*///		throw emu_fatalerror(EMU_ERR_NO_SUCH_SYSTEM, "No matching systems found for '%s'", gamename);
/*TODO*///
/*TODO*///	// iterate over drivers, looking for SAMPLES devices
/*TODO*///	bool first = true;
/*TODO*///	while (drivlist.next())
/*TODO*///	{
/*TODO*///		// see if we have samples
/*TODO*///		samples_device_iterator iter(drivlist.config()->root_device());
/*TODO*///		if (iter.count() == 0)
/*TODO*///			continue;
/*TODO*///
/*TODO*///		// print a header
/*TODO*///		if (!first)
/*TODO*///			osd_printf_info("\n");
/*TODO*///		first = false;
/*TODO*///		osd_printf_info("Samples required for driver \"%s\".\n", drivlist.driver().name);
/*TODO*///
/*TODO*///		// iterate over samples devices and print the samples from each one
/*TODO*///		for (samples_device &device : iter)
/*TODO*///		{
/*TODO*///			samples_iterator sampiter(device);
/*TODO*///			for (const char *samplename = sampiter.first(); samplename != nullptr; samplename = sampiter.next())
/*TODO*///				osd_printf_info("%s\n", samplename);
/*TODO*///		}
/*TODO*///	}
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  listdevices - output the list of devices
/*TODO*/////  referenced by a given game or set of games
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///void cli_frontend::listdevices(const std::vector<std::string> &args)
/*TODO*///{
/*TODO*///	const char *gamename = args.empty() ? nullptr : args[0].c_str();
/*TODO*///
/*TODO*///	// determine which drivers to output; return an error if none found
/*TODO*///	driver_enumerator drivlist(m_options, gamename);
/*TODO*///	if (drivlist.count() == 0)
/*TODO*///		throw emu_fatalerror(EMU_ERR_NO_SUCH_SYSTEM, "No matching systems found for '%s'", gamename);
/*TODO*///
/*TODO*///	// iterate over drivers, looking for SAMPLES devices
/*TODO*///	bool first = true;
/*TODO*///	while (drivlist.next())
/*TODO*///	{
/*TODO*///		// print a header
/*TODO*///		if (!first)
/*TODO*///			printf("\n");
/*TODO*///		first = false;
/*TODO*///		printf("Driver %s (%s):\n", drivlist.driver().name, drivlist.driver().type.fullname());
/*TODO*///
/*TODO*///		// build a list of devices
/*TODO*///		std::vector<device_t *> device_list;
/*TODO*///		for (device_t &device : device_iterator(drivlist.config()->root_device()))
/*TODO*///			device_list.push_back(&device);
/*TODO*///
/*TODO*///		// sort them by tag
/*TODO*///		std::sort(device_list.begin(), device_list.end(), [](device_t *dev1, device_t *dev2) {
/*TODO*///			// end of string < ':' < '0'
/*TODO*///			const char *tag1 = dev1->tag();
/*TODO*///			const char *tag2 = dev2->tag();
/*TODO*///			while (*tag1 == *tag2 && *tag1 != '\0' && *tag2 != '\0')
/*TODO*///			{
/*TODO*///				tag1++;
/*TODO*///				tag2++;
/*TODO*///			}
/*TODO*///			return (*tag1 == ':' ? ' ' : *tag1) < (*tag2 == ':' ? ' ' : *tag2);
/*TODO*///		});
/*TODO*///
/*TODO*///		// dump the results
/*TODO*///		for (auto device : device_list)
/*TODO*///		{
/*TODO*///			// extract the tag, stripping the leading colon
/*TODO*///			const char *tag = device->tag();
/*TODO*///			if (*tag == ':')
/*TODO*///				tag++;
/*TODO*///
/*TODO*///			// determine the depth
/*TODO*///			int depth = 1;
/*TODO*///			if (*tag == 0)
/*TODO*///			{
/*TODO*///				tag = "<root>";
/*TODO*///				depth = 0;
/*TODO*///			}
/*TODO*///			else
/*TODO*///			{
/*TODO*///				for (const char *c = tag; *c != 0; c++)
/*TODO*///					if (*c == ':')
/*TODO*///					{
/*TODO*///						tag = c + 1;
/*TODO*///						depth++;
/*TODO*///					}
/*TODO*///			}
/*TODO*///			printf("   %*s%-*s %s", depth * 2, "", 30 - depth * 2, tag, device->name());
/*TODO*///
/*TODO*///			// add more information
/*TODO*///			uint32_t clock = device->clock();
/*TODO*///			if (clock >= 1000000000)
/*TODO*///				printf(" @ %d.%02d GHz\n", clock / 1000000000, (clock / 10000000) % 100);
/*TODO*///			else if (clock >= 1000000)
/*TODO*///				printf(" @ %d.%02d MHz\n", clock / 1000000, (clock / 10000) % 100);
/*TODO*///			else if (clock >= 1000)
/*TODO*///				printf(" @ %d.%02d kHz\n", clock / 1000, (clock / 10) % 100);
/*TODO*///			else if (clock > 0)
/*TODO*///				printf(" @ %d Hz\n", clock);
/*TODO*///			else
/*TODO*///				printf("\n");
/*TODO*///		}
/*TODO*///	}
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  listslots - output the list of slot devices
/*TODO*/////  referenced by a given game or set of games
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///void cli_frontend::listslots(const std::vector<std::string> &args)
/*TODO*///{
/*TODO*///	const char *gamename = args.empty() ? nullptr : args[0].c_str();
/*TODO*///
/*TODO*///	// determine which drivers to output; return an error if none found
/*TODO*///	driver_enumerator drivlist(m_options, gamename);
/*TODO*///	if (drivlist.count() == 0)
/*TODO*///		throw emu_fatalerror(EMU_ERR_NO_SUCH_SYSTEM, "No matching systems found for '%s'", gamename);
/*TODO*///
/*TODO*///	// print header
/*TODO*///	printf("%-16s %-16s %-16s %s\n", "SYSTEM", "SLOT NAME", "SLOT OPTIONS", "SLOT DEVICE NAME");
/*TODO*///	printf("%s %s %s %s\n", std::string(16,'-').c_str(), std::string(16,'-').c_str(), std::string(16,'-').c_str(), std::string(28,'-').c_str());
/*TODO*///
/*TODO*///	// iterate over drivers
/*TODO*///	while (drivlist.next())
/*TODO*///	{
/*TODO*///		// iterate
/*TODO*///		bool first = true;
/*TODO*///		for (const device_slot_interface &slot : slot_interface_iterator(drivlist.config()->root_device()))
/*TODO*///		{
/*TODO*///			if (slot.fixed()) continue;
/*TODO*///
/*TODO*///			// build a list of user-selectable options
/*TODO*///			std::vector<device_slot_interface::slot_option const *> option_list;
/*TODO*///			for (auto &option : slot.option_list())
/*TODO*///				if (option.second->selectable())
/*TODO*///					option_list.push_back(option.second.get());
/*TODO*///
/*TODO*///			// sort them by name
/*TODO*///			std::sort(option_list.begin(), option_list.end(), [](device_slot_interface::slot_option const *opt1, device_slot_interface::slot_option const *opt2) {
/*TODO*///				return strcmp(opt1->name(), opt2->name()) < 0;
/*TODO*///			});
/*TODO*///
/*TODO*///
/*TODO*///			// output the line, up to the list of extensions
/*TODO*///			printf("%-16s %-16s ", first ? drivlist.driver().name : "", slot.device().tag()+1);
/*TODO*///
/*TODO*///			bool first_option = true;
/*TODO*///
/*TODO*///			// get the options and print them
/*TODO*///			for (device_slot_interface::slot_option const *opt : option_list)
/*TODO*///			{
/*TODO*///				if (first_option)
/*TODO*///					printf("%-16s %s\n", opt->name(), opt->devtype().fullname());
/*TODO*///				else
/*TODO*///					printf("%-34s%-16s %s\n", "", opt->name(), opt->devtype().fullname());
/*TODO*///
/*TODO*///				first_option = false;
/*TODO*///			}
/*TODO*///			if (first_option)
/*TODO*///				printf("%-16s %s\n", "[none]","No options available");
/*TODO*///			// end the line
/*TODO*///			printf("\n");
/*TODO*///			first = false;
/*TODO*///		}
/*TODO*///
/*TODO*///		// if we didn't get any at all, just print a none line
/*TODO*///		if (first)
/*TODO*///			printf("%-16s (none)\n", drivlist.driver().name);
/*TODO*///	}
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  listmedia - output the list of image devices
/*TODO*/////  referenced by a given game or set of games
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///void cli_frontend::listmedia(const std::vector<std::string> &args)
/*TODO*///{
/*TODO*///	const char *gamename = args.empty() ? nullptr : args[0].c_str();
/*TODO*///
/*TODO*///	// determine which drivers to output; return an error if none found
/*TODO*///	driver_enumerator drivlist(m_options, gamename);
/*TODO*///	if (drivlist.count() == 0)
/*TODO*///		throw emu_fatalerror(EMU_ERR_NO_SUCH_SYSTEM, "No matching systems found for '%s'", gamename);
/*TODO*///
/*TODO*///	// print header
/*TODO*///	printf("%-16s %-16s %-10s %s\n", "SYSTEM", "MEDIA NAME", "(brief)", "IMAGE FILE EXTENSIONS SUPPORTED");
/*TODO*///	printf("%s %s-%s %s\n", std::string(16,'-').c_str(), std::string(16,'-').c_str(), std::string(10,'-').c_str(), std::string(31,'-').c_str());
/*TODO*///
/*TODO*///	// iterate over drivers
/*TODO*///	while (drivlist.next())
/*TODO*///	{
/*TODO*///		// iterate
/*TODO*///		bool first = true;
/*TODO*///		for (const device_image_interface &imagedev : image_interface_iterator(drivlist.config()->root_device()))
/*TODO*///		{
/*TODO*///			if (!imagedev.user_loadable())
/*TODO*///				continue;
/*TODO*///
/*TODO*///			// extract the shortname with parentheses
/*TODO*///			std::string paren_shortname = string_format("(%s)", imagedev.brief_instance_name());
/*TODO*///
/*TODO*///			// output the line, up to the list of extensions
/*TODO*///			printf("%-16s %-16s %-10s ", first ? drivlist.driver().name : "", imagedev.instance_name().c_str(), paren_shortname.c_str());
/*TODO*///
/*TODO*///			// get the extensions and print them
/*TODO*///			std::string extensions(imagedev.file_extensions());
/*TODO*///			for (int start = 0, end = extensions.find_first_of(',');; start = end + 1, end = extensions.find_first_of(',', start))
/*TODO*///			{
/*TODO*///				std::string curext(extensions, start, (end == -1) ? extensions.length() - start : end - start);
/*TODO*///				printf(".%-5s", curext.c_str());
/*TODO*///				if (end == -1)
/*TODO*///					break;
/*TODO*///			}
/*TODO*///
/*TODO*///			// end the line
/*TODO*///			printf("\n");
/*TODO*///			first = false;
/*TODO*///		}
/*TODO*///
/*TODO*///		// if we didn't get any at all, just print a none line
/*TODO*///		if (first)
/*TODO*///			printf("%-16s (none)\n", drivlist.driver().name);
/*TODO*///	}
/*TODO*///}
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  verifyroms - verify the ROM sets of one or
/*TODO*/////  more games
/*TODO*/////-------------------------------------------------
/*TODO*///void cli_frontend::verifyroms(const std::vector<std::string> &args)
/*TODO*///{
/*TODO*///	bool const iswild((1U != args.size()) || core_iswildstr(args[0].c_str()));
/*TODO*///	std::vector<bool> matched(args.size(), false);
/*TODO*///	unsigned matchcount = 0;
/*TODO*///	auto const included = [&args, &matched, &matchcount] (char const *name) -> bool
/*TODO*///	{
/*TODO*///		if (args.empty())
/*TODO*///		{
/*TODO*///			++matchcount;
/*TODO*///			return true;
/*TODO*///		}
/*TODO*///
/*TODO*///		bool result = false;
/*TODO*///		auto it = matched.begin();
/*TODO*///		for (std::string const &pat : args)
/*TODO*///		{
/*TODO*///			if (!core_strwildcmp(pat.c_str(), name))
/*TODO*///			{
/*TODO*///				++matchcount;
/*TODO*///				result = true;
/*TODO*///				*it = true;
/*TODO*///			}
/*TODO*///			++it;
/*TODO*///		}
/*TODO*///		return result;
/*TODO*///	};
/*TODO*///
/*TODO*///	unsigned correct = 0;
/*TODO*///	unsigned incorrect = 0;
/*TODO*///	unsigned notfound = 0;
/*TODO*///
/*TODO*///	// iterate over drivers
/*TODO*///	driver_enumerator drivlist(m_options);
/*TODO*///	media_auditor auditor(drivlist);
/*TODO*///	util::ovectorstream summary_string;
/*TODO*///	while (drivlist.next())
/*TODO*///	{
/*TODO*///		if (included(drivlist.driver().name))
/*TODO*///		{
/*TODO*///			// audit the ROMs in this set
/*TODO*///			media_auditor::summary summary = auditor.audit_media(AUDIT_VALIDATE_FAST);
/*TODO*///
/*TODO*///			auto const clone_of = drivlist.clone();
/*TODO*///			print_summary(
/*TODO*///					auditor, summary, true,
/*TODO*///					"rom", drivlist.driver().name, (clone_of >= 0) ? drivlist.driver(clone_of).name : nullptr,
/*TODO*///					correct, incorrect, notfound,
/*TODO*///					summary_string);
/*TODO*///
/*TODO*///			// if it wasn't a wildcard, there can only be one
/*TODO*///			if (!iswild)
/*TODO*///				break;
/*TODO*///		}
/*TODO*///	}
/*TODO*///
/*TODO*///	if (iswild || !matchcount)
/*TODO*///	{
/*TODO*///		machine_config config(GAME_NAME(___empty), m_options);
/*TODO*///		machine_config::token const tok(config.begin_configuration(config.root_device()));
/*TODO*///		for (device_type type : registered_device_types)
/*TODO*///		{
/*TODO*///			if (included(type.shortname()))
/*TODO*///			{
/*TODO*///				// audit the ROMs in this set
/*TODO*///				device_t *const dev = config.device_add("_tmp", type, 0);
/*TODO*///				media_auditor::summary summary = auditor.audit_device(*dev, AUDIT_VALIDATE_FAST);
/*TODO*///
/*TODO*///				print_summary(
/*TODO*///						auditor, summary, false,
/*TODO*///						"rom", dev->shortname(), nullptr,
/*TODO*///						correct, incorrect, notfound,
/*TODO*///						summary_string);
/*TODO*///				config.device_remove("_tmp");
/*TODO*///
/*TODO*///				// if it wasn't a wildcard, there can only be one
/*TODO*///				if (!iswild)
/*TODO*///					break;
/*TODO*///			}
/*TODO*///		}
/*TODO*///	}
/*TODO*///
/*TODO*///	// clear out any cached files
/*TODO*///	util::archive_file::cache_clear();
/*TODO*///
/*TODO*///	// return an error if none found
/*TODO*///	auto it = matched.begin();
/*TODO*///	for (std::string const &pat : args)
/*TODO*///	{
/*TODO*///		if (!*it)
/*TODO*///			throw emu_fatalerror(EMU_ERR_NO_SUCH_SYSTEM, "No matching systems found for '%s'", pat.c_str());
/*TODO*///
/*TODO*///		++it;
/*TODO*///	}
/*TODO*///
/*TODO*///	if ((1U == args.size()) && (matchcount > 0) && (correct == 0) && (incorrect == 0))
/*TODO*///	{
/*TODO*///		// if we didn't get anything at all, display a generic end message
/*TODO*///		if (notfound > 0)
/*TODO*///			throw emu_fatalerror(EMU_ERR_MISSING_FILES, "romset \"%s\" not found!\n", args[0].c_str());
/*TODO*///		else
/*TODO*///			throw emu_fatalerror(EMU_ERR_MISSING_FILES, "romset \"%s\" has no roms!\n", args[0].c_str());
/*TODO*///	}
/*TODO*///	else
/*TODO*///	{
/*TODO*///		// otherwise, print a summary
/*TODO*///		if (incorrect > 0)
/*TODO*///			throw emu_fatalerror(EMU_ERR_MISSING_FILES, "%u romsets found, %u were OK.\n", correct + incorrect, correct);
/*TODO*///		else
/*TODO*///			osd_printf_info("%u romsets found, %u were OK.\n", correct, correct);
/*TODO*///	}
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  info_verifysamples - verify the sample sets of
/*TODO*/////  one or more games
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///void cli_frontend::verifysamples(const std::vector<std::string> &args)
/*TODO*///{
/*TODO*///	const char *gamename = args.empty() ? "*" : args[0].c_str();
/*TODO*///
/*TODO*///	// determine which drivers to output; return an error if none found
/*TODO*///	driver_enumerator drivlist(m_options, gamename);
/*TODO*///
/*TODO*///	unsigned correct = 0;
/*TODO*///	unsigned incorrect = 0;
/*TODO*///	unsigned notfound = 0;
/*TODO*///	unsigned matched = 0;
/*TODO*///
/*TODO*///	// iterate over drivers
/*TODO*///	media_auditor auditor(drivlist);
/*TODO*///	util::ovectorstream summary_string;
/*TODO*///	while (drivlist.next())
/*TODO*///	{
/*TODO*///		matched++;
/*TODO*///
/*TODO*///		// audit the samples in this set
/*TODO*///		media_auditor::summary summary = auditor.audit_samples();
/*TODO*///
/*TODO*///		auto const clone_of = drivlist.clone();
/*TODO*///		print_summary(
/*TODO*///				auditor, summary, false,
/*TODO*///				"sample", drivlist.driver().name, (clone_of >= 0) ? drivlist.driver(clone_of).name : nullptr,
/*TODO*///				correct, incorrect, notfound,
/*TODO*///				summary_string);
/*TODO*///	}
/*TODO*///
/*TODO*///	// clear out any cached files
/*TODO*///	util::archive_file::cache_clear();
/*TODO*///
/*TODO*///	// return an error if none found
/*TODO*///	if (matched == 0)
/*TODO*///		throw emu_fatalerror(EMU_ERR_NO_SUCH_SYSTEM, "No matching systems found for '%s'", gamename);
/*TODO*///
/*TODO*///	// if we didn't get anything at all, display a generic end message
/*TODO*///	if (matched > 0 && correct == 0 && incorrect == 0)
/*TODO*///	{
/*TODO*///		if (notfound > 0)
/*TODO*///			throw emu_fatalerror(EMU_ERR_MISSING_FILES, "sampleset \"%s\" not found!\n", gamename);
/*TODO*///		else
/*TODO*///			throw emu_fatalerror(EMU_ERR_MISSING_FILES, "sampleset \"%s\" not required!\n", gamename);
/*TODO*///	}
/*TODO*///
/*TODO*///	// otherwise, print a summary
/*TODO*///	else
/*TODO*///	{
/*TODO*///		if (incorrect > 0)
/*TODO*///			throw emu_fatalerror(EMU_ERR_MISSING_FILES, "%u samplesets found, %u were OK.\n", correct + incorrect, correct);
/*TODO*///		osd_printf_info("%u samplesets found, %u were OK.\n", correct, correct);
/*TODO*///	}
/*TODO*///}
/*TODO*///
/*TODO*///const char cli_frontend::s_softlist_xml_dtd[] =
/*TODO*///				"<?xml version=\"1.0\"?>\n" \
/*TODO*///				"<!DOCTYPE softwarelists [\n" \
/*TODO*///				"<!ELEMENT softwarelists (softwarelist*)>\n" \
/*TODO*///				"\t<!ELEMENT softwarelist (software+)>\n" \
/*TODO*///				"\t\t<!ATTLIST softwarelist name CDATA #REQUIRED>\n" \
/*TODO*///				"\t\t<!ATTLIST softwarelist description CDATA #IMPLIED>\n" \
/*TODO*///				"\t\t<!ELEMENT software (description, year, publisher, info*, sharedfeat*, part*)>\n" \
/*TODO*///				"\t\t\t<!ATTLIST software name CDATA #REQUIRED>\n" \
/*TODO*///				"\t\t\t<!ATTLIST software cloneof CDATA #IMPLIED>\n" \
/*TODO*///				"\t\t\t<!ATTLIST software supported (yes|partial|no) \"yes\">\n" \
/*TODO*///				"\t\t\t<!ELEMENT description (#PCDATA)>\n" \
/*TODO*///				"\t\t\t<!ELEMENT year (#PCDATA)>\n" \
/*TODO*///				"\t\t\t<!ELEMENT publisher (#PCDATA)>\n" \
/*TODO*///				"\t\t\t<!ELEMENT info EMPTY>\n" \
/*TODO*///				"\t\t\t\t<!ATTLIST info name CDATA #REQUIRED>\n" \
/*TODO*///				"\t\t\t\t<!ATTLIST info value CDATA #IMPLIED>\n" \
/*TODO*///				"\t\t\t<!ELEMENT sharedfeat EMPTY>\n" \
/*TODO*///				"\t\t\t\t<!ATTLIST sharedfeat name CDATA #REQUIRED>\n" \
/*TODO*///				"\t\t\t\t<!ATTLIST sharedfeat value CDATA #IMPLIED>\n" \
/*TODO*///				"\t\t\t<!ELEMENT part (feature*, dataarea*, diskarea*, dipswitch*)>\n" \
/*TODO*///				"\t\t\t\t<!ATTLIST part name CDATA #REQUIRED>\n" \
/*TODO*///				"\t\t\t\t<!ATTLIST part interface CDATA #REQUIRED>\n" \
/*TODO*///				"\t\t\t\t<!ELEMENT feature EMPTY>\n" \
/*TODO*///				"\t\t\t\t\t<!ATTLIST feature name CDATA #REQUIRED>\n" \
/*TODO*///				"\t\t\t\t\t<!ATTLIST feature value CDATA #IMPLIED>\n" \
/*TODO*///				"\t\t\t\t<!ELEMENT dataarea (rom*)>\n" \
/*TODO*///				"\t\t\t\t\t<!ATTLIST dataarea name CDATA #REQUIRED>\n" \
/*TODO*///				"\t\t\t\t\t<!ATTLIST dataarea size CDATA #REQUIRED>\n" \
/*TODO*///				"\t\t\t\t\t<!ATTLIST dataarea databits (8|16|32|64) \"8\">\n" \
/*TODO*///				"\t\t\t\t\t<!ATTLIST dataarea endian (big|little) \"little\">\n" \
/*TODO*///				"\t\t\t\t\t<!ELEMENT rom EMPTY>\n" \
/*TODO*///				"\t\t\t\t\t\t<!ATTLIST rom name CDATA #IMPLIED>\n" \
/*TODO*///				"\t\t\t\t\t\t<!ATTLIST rom size CDATA #IMPLIED>\n" \
/*TODO*///				"\t\t\t\t\t\t<!ATTLIST rom length CDATA #IMPLIED>\n" \
/*TODO*///				"\t\t\t\t\t\t<!ATTLIST rom crc CDATA #IMPLIED>\n" \
/*TODO*///				"\t\t\t\t\t\t<!ATTLIST rom sha1 CDATA #IMPLIED>\n" \
/*TODO*///				"\t\t\t\t\t\t<!ATTLIST rom offset CDATA #IMPLIED>\n" \
/*TODO*///				"\t\t\t\t\t\t<!ATTLIST rom value CDATA #IMPLIED>\n" \
/*TODO*///				"\t\t\t\t\t\t<!ATTLIST rom status (baddump|nodump|good) \"good\">\n" \
/*TODO*///				"\t\t\t\t\t\t<!ATTLIST rom loadflag (load16_byte|load16_word|load16_word_swap|load32_byte|load32_word|load32_word_swap|load32_dword|load64_word|load64_word_swap|reload|fill|continue|reload_plain) #IMPLIED>\n" \
/*TODO*///				"\t\t\t\t<!ELEMENT diskarea (disk*)>\n" \
/*TODO*///				"\t\t\t\t\t<!ATTLIST diskarea name CDATA #REQUIRED>\n" \
/*TODO*///				"\t\t\t\t\t<!ELEMENT disk EMPTY>\n" \
/*TODO*///				"\t\t\t\t\t\t<!ATTLIST disk name CDATA #REQUIRED>\n" \
/*TODO*///				"\t\t\t\t\t\t<!ATTLIST disk sha1 CDATA #IMPLIED>\n" \
/*TODO*///				"\t\t\t\t\t\t<!ATTLIST disk status (baddump|nodump|good) \"good\">\n" \
/*TODO*///				"\t\t\t\t\t\t<!ATTLIST disk writeable (yes|no) \"no\">\n" \
/*TODO*///				"\t\t\t\t<!ELEMENT dipswitch (dipvalue*)>\n" \
/*TODO*///				"\t\t\t\t\t<!ATTLIST dipswitch name CDATA #REQUIRED>\n" \
/*TODO*///				"\t\t\t\t\t<!ATTLIST dipswitch tag CDATA #REQUIRED>\n" \
/*TODO*///				"\t\t\t\t\t<!ATTLIST dipswitch mask CDATA #REQUIRED>\n" \
/*TODO*///				"\t\t\t\t\t<!ELEMENT dipvalue EMPTY>\n" \
/*TODO*///				"\t\t\t\t\t\t<!ATTLIST dipvalue name CDATA #REQUIRED>\n" \
/*TODO*///				"\t\t\t\t\t\t<!ATTLIST dipvalue value CDATA #REQUIRED>\n" \
/*TODO*///				"\t\t\t\t\t\t<!ATTLIST dipvalue default (yes|no) \"no\">\n" \
/*TODO*///				"]>\n\n";
/*TODO*///
/*TODO*///void cli_frontend::output_single_softlist(std::ostream &out, software_list_device &swlistdev)
/*TODO*///{
/*TODO*///	util::stream_format(out, "\t<softwarelist name=\"%s\" description=\"%s\">\n", swlistdev.list_name(), util::xml::normalize_string(swlistdev.description().c_str()));
/*TODO*///	for (const software_info &swinfo : swlistdev.get_info())
/*TODO*///	{
/*TODO*///		util::stream_format(out, "\t\t<software name=\"%s\"", util::xml::normalize_string(swinfo.shortname().c_str()));
/*TODO*///		if (!swinfo.parentname().empty())
/*TODO*///			util::stream_format(out, " cloneof=\"%s\"", util::xml::normalize_string(swinfo.parentname().c_str()));
/*TODO*///		if (swinfo.supported() == SOFTWARE_SUPPORTED_PARTIAL)
/*TODO*///			out << " supported=\"partial\"";
/*TODO*///		if (swinfo.supported() == SOFTWARE_SUPPORTED_NO)
/*TODO*///			out << " supported=\"no\"";
/*TODO*///		out << ">\n";
/*TODO*///		util::stream_format(out, "\t\t\t<description>%s</description>\n", util::xml::normalize_string(swinfo.longname().c_str()));
/*TODO*///		util::stream_format(out, "\t\t\t<year>%s</year>\n", util::xml::normalize_string(swinfo.year().c_str()));
/*TODO*///		util::stream_format(out, "\t\t\t<publisher>%s</publisher>\n", util::xml::normalize_string(swinfo.publisher().c_str()));
/*TODO*///
/*TODO*///		for (const feature_list_item &flist : swinfo.other_info())
/*TODO*///			util::stream_format(out, "\t\t\t<info name=\"%s\" value=\"%s\"/>\n", flist.name().c_str(), util::xml::normalize_string(flist.value().c_str()));
/*TODO*///
/*TODO*///		for (const software_part &part : swinfo.parts())
/*TODO*///		{
/*TODO*///			util::stream_format(out, "\t\t\t<part name=\"%s\"", util::xml::normalize_string(part.name().c_str()));
/*TODO*///			if (!part.interface().empty())
/*TODO*///				util::stream_format(out, " interface=\"%s\"", util::xml::normalize_string(part.interface().c_str()));
/*TODO*///
/*TODO*///			out << ">\n";
/*TODO*///
/*TODO*///			for (const feature_list_item &flist : part.featurelist())
/*TODO*///				util::stream_format(out, "\t\t\t\t<feature name=\"%s\" value=\"%s\" />\n", flist.name().c_str(), util::xml::normalize_string(flist.value().c_str()));
/*TODO*///
/*TODO*///			// TODO: display ROM region information
/*TODO*///			for (const rom_entry *region = part.romdata().data(); region; region = rom_next_region(region))
/*TODO*///			{
/*TODO*///				int is_disk = ROMREGION_ISDISKDATA(region);
/*TODO*///
/*TODO*///				if (!is_disk)
/*TODO*///					util::stream_format(out, "\t\t\t\t<dataarea name=\"%s\" size=\"%d\">\n", util::xml::normalize_string(ROMREGION_GETTAG(region)), ROMREGION_GETLENGTH(region));
/*TODO*///				else
/*TODO*///					util::stream_format(out, "\t\t\t\t<diskarea name=\"%s\">\n", util::xml::normalize_string(ROMREGION_GETTAG(region)));
/*TODO*///
/*TODO*///				for (const rom_entry *rom = rom_first_file(region); rom && !ROMENTRY_ISREGIONEND(rom); rom++)
/*TODO*///				{
/*TODO*///					if (ROMENTRY_ISFILE(rom))
/*TODO*///					{
/*TODO*///						if (!is_disk)
/*TODO*///							util::stream_format(out, "\t\t\t\t\t<rom name=\"%s\" size=\"%d\"", util::xml::normalize_string(ROM_GETNAME(rom)), rom_file_size(rom));
/*TODO*///						else
/*TODO*///							util::stream_format(out, "\t\t\t\t\t<disk name=\"%s\"", util::xml::normalize_string(ROM_GETNAME(rom)));
/*TODO*///
/*TODO*///						// dump checksum information only if there is a known dump
/*TODO*///						util::hash_collection hashes(ROM_GETHASHDATA(rom));
/*TODO*///						if (!hashes.flag(util::hash_collection::FLAG_NO_DUMP))
/*TODO*///							util::stream_format(out, " %s", hashes.attribute_string());
/*TODO*///						else
/*TODO*///							out << " status=\"nodump\"";
/*TODO*///
/*TODO*///						if (is_disk)
/*TODO*///							util::stream_format(out, " writeable=\"%s\"", (ROM_GETFLAGS(rom) & DISK_READONLYMASK) ? "no" : "yes");
/*TODO*///
/*TODO*///						if ((ROM_GETFLAGS(rom) & ROM_SKIPMASK) == ROM_SKIP(1))
/*TODO*///							out << " loadflag=\"load16_byte\"";
/*TODO*///
/*TODO*///						if ((ROM_GETFLAGS(rom) & ROM_SKIPMASK) == ROM_SKIP(3))
/*TODO*///							out << " loadflag=\"load32_byte\"";
/*TODO*///
/*TODO*///						if (((ROM_GETFLAGS(rom) & ROM_SKIPMASK) == ROM_SKIP(2)) && ((ROM_GETFLAGS(rom) & ROM_GROUPMASK) == ROM_GROUPWORD))
/*TODO*///						{
/*TODO*///							if (!(ROM_GETFLAGS(rom) & ROM_REVERSEMASK))
/*TODO*///								out << " loadflag=\"load32_word\"";
/*TODO*///							else
/*TODO*///								out << " loadflag=\"load32_word_swap\"";
/*TODO*///						}
/*TODO*///
/*TODO*///						if (((ROM_GETFLAGS(rom) & ROM_SKIPMASK) == ROM_SKIP(6)) && ((ROM_GETFLAGS(rom) & ROM_GROUPMASK) == ROM_GROUPWORD))
/*TODO*///						{
/*TODO*///							if (!(ROM_GETFLAGS(rom) & ROM_REVERSEMASK))
/*TODO*///								out << " loadflag=\"load64_word\"";
/*TODO*///							else
/*TODO*///								out << " loadflag=\"load64_word_swap\"";
/*TODO*///						}
/*TODO*///
/*TODO*///						if (((ROM_GETFLAGS(rom) & ROM_SKIPMASK) == ROM_NOSKIP) && ((ROM_GETFLAGS(rom) & ROM_GROUPMASK) == ROM_GROUPWORD))
/*TODO*///						{
/*TODO*///							if (!(ROM_GETFLAGS(rom) & ROM_REVERSEMASK))
/*TODO*///								out << " loadflag=\"load32_dword\"";
/*TODO*///							else
/*TODO*///								out << " loadflag=\"load16_word_swap\"";
/*TODO*///						}
/*TODO*///
/*TODO*///						out << "/>\n";
/*TODO*///					}
/*TODO*///					else if (ROMENTRY_ISRELOAD(rom))
/*TODO*///					{
/*TODO*///						util::stream_format(out, "\t\t\t\t\t<rom size=\"%d\" offset=\"0x%x\" loadflag=\"reload\" />\n", ROM_GETLENGTH(rom), ROM_GETOFFSET(rom));
/*TODO*///					}
/*TODO*///					else if (ROMENTRY_ISFILL(rom))
/*TODO*///					{
/*TODO*///						util::stream_format(out, "\t\t\t\t\t<rom size=\"%d\" offset=\"0x%x\" loadflag=\"fill\" />\n", ROM_GETLENGTH(rom), ROM_GETOFFSET(rom));
/*TODO*///					}
/*TODO*///				}
/*TODO*///
/*TODO*///				if (!is_disk)
/*TODO*///					out << "\t\t\t\t</dataarea>\n";
/*TODO*///				else
/*TODO*///					out << "\t\t\t\t</diskarea>\n";
/*TODO*///			}
/*TODO*///
/*TODO*///			out << "\t\t\t</part>\n";
/*TODO*///		}
/*TODO*///
/*TODO*///		out << "\t\t</software>\n";
/*TODO*///	}
/*TODO*///	out << "\t</softwarelist>\n";
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*////*-------------------------------------------------
/*TODO*///    info_listsoftware - output the list of
/*TODO*///    software supported by a given game or set of
/*TODO*///    games
/*TODO*///    TODO: Add all information read from the source files
/*TODO*///-------------------------------------------------*/
/*TODO*///
/*TODO*///void cli_frontend::listsoftware(const std::vector<std::string> &args)
/*TODO*///{
/*TODO*///	std::unordered_set<std::string> list_map;
/*TODO*///	bool firstlist(true);
/*TODO*///	apply_device_action(
/*TODO*///			args,
/*TODO*///			[this, &list_map, &firstlist] (device_t &root, char const *type, bool first)
/*TODO*///			{
/*TODO*///				for (software_list_device &swlistdev : software_list_device_iterator(root))
/*TODO*///				{
/*TODO*///					if (list_map.insert(swlistdev.list_name()).second)
/*TODO*///					{
/*TODO*///						if (!swlistdev.get_info().empty())
/*TODO*///						{
/*TODO*///							if (firstlist)
/*TODO*///							{
/*TODO*///								if (m_options.bool_value(CLIOPTION_DTD))
/*TODO*///									std::cout << s_softlist_xml_dtd;
/*TODO*///								std::cout << "<softwarelists>\n";
/*TODO*///								firstlist = false;
/*TODO*///							}
/*TODO*///							output_single_softlist(std::cout, swlistdev);
/*TODO*///						}
/*TODO*///					}
/*TODO*///				}
/*TODO*///			});
/*TODO*///
/*TODO*///	if (!firstlist)
/*TODO*///		std::cout << "</softwarelists>\n";
/*TODO*///	else
/*TODO*///		fprintf(stdout, "No software lists found for this system\n"); // TODO: should this go to stderr instead?
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*////*-------------------------------------------------
/*TODO*///    verifysoftware - verify ROMs from the software
/*TODO*///    list of the specified driver(s)
/*TODO*///-------------------------------------------------*/
/*TODO*///void cli_frontend::verifysoftware(const std::vector<std::string> &args)
/*TODO*///{
/*TODO*///	const char *gamename = args.empty() ? "*" : args[0].c_str();
/*TODO*///
/*TODO*///	std::unordered_set<std::string> list_map;
/*TODO*///
/*TODO*///	unsigned correct = 0;
/*TODO*///	unsigned incorrect = 0;
/*TODO*///	unsigned notfound = 0;
/*TODO*///	unsigned matched = 0;
/*TODO*///	unsigned nrlists = 0;
/*TODO*///
/*TODO*///	// determine which drivers to process; return an error if none found
/*TODO*///	driver_enumerator drivlist(m_options, gamename);
/*TODO*///	if (drivlist.count() == 0)
/*TODO*///		throw emu_fatalerror(EMU_ERR_NO_SUCH_SYSTEM, "No matching systems found for '%s'", gamename);
/*TODO*///
/*TODO*///	media_auditor auditor(drivlist);
/*TODO*///	util::ovectorstream summary_string;
/*TODO*///	while (drivlist.next())
/*TODO*///	{
/*TODO*///		matched++;
/*TODO*///
/*TODO*///		for (software_list_device &swlistdev : software_list_device_iterator(drivlist.config()->root_device()))
/*TODO*///		{
/*TODO*///			if (swlistdev.is_original())
/*TODO*///			{
/*TODO*///				if (list_map.insert(swlistdev.list_name()).second)
/*TODO*///				{
/*TODO*///					if (!swlistdev.get_info().empty())
/*TODO*///					{
/*TODO*///						nrlists++;
/*TODO*///						for (const software_info &swinfo : swlistdev.get_info())
/*TODO*///						{
/*TODO*///							media_auditor::summary summary = auditor.audit_software(swlistdev, swinfo, AUDIT_VALIDATE_FAST);
/*TODO*///
/*TODO*///							print_summary(
/*TODO*///									auditor, summary, false,
/*TODO*///									"rom", util::string_format("%s:%s", swlistdev.list_name(), swinfo.shortname()).c_str(), nullptr,
/*TODO*///									correct, incorrect, notfound,
/*TODO*///									summary_string);
/*TODO*///						}
/*TODO*///					}
/*TODO*///				}
/*TODO*///			}
/*TODO*///		}
/*TODO*///	}
/*TODO*///
/*TODO*///	// clear out any cached files
/*TODO*///	util::archive_file::cache_clear();
/*TODO*///
/*TODO*///	// return an error if none found
/*TODO*///	if (matched == 0)
/*TODO*///		throw emu_fatalerror(EMU_ERR_NO_SUCH_SYSTEM, "No matching systems found for '%s'", gamename);
/*TODO*///
/*TODO*///	// if we didn't get anything at all, display a generic end message
/*TODO*///	if (matched > 0 && correct == 0 && incorrect == 0)
/*TODO*///	{
/*TODO*///		throw emu_fatalerror(EMU_ERR_MISSING_FILES, "romset \"%s\" has no software entries defined!\n", gamename);
/*TODO*///	}
/*TODO*///	// otherwise, print a summary
/*TODO*///	else
/*TODO*///	{
/*TODO*///		if (incorrect > 0)
/*TODO*///			throw emu_fatalerror(EMU_ERR_MISSING_FILES, "%u romsets found in %u software lists, %u were OK.\n", correct + incorrect, nrlists, correct);
/*TODO*///		osd_printf_info("%u romsets found in %u software lists, %u romsets were OK.\n", correct, nrlists, correct);
/*TODO*///	}
/*TODO*///
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*////*-------------------------------------------------
/*TODO*///    getsoftlist - retrieve software list by name
/*TODO*///-------------------------------------------------*/
/*TODO*///
/*TODO*///void cli_frontend::getsoftlist(const std::vector<std::string> &args)
/*TODO*///{
/*TODO*///	const char *gamename = args.empty() ? "*" : args[0].c_str();
/*TODO*///
/*TODO*///	std::unordered_set<std::string> list_map;
/*TODO*///	bool firstlist(true);
/*TODO*///	apply_device_action(
/*TODO*///			std::vector<std::string>(),
/*TODO*///			[this, gamename, &list_map, &firstlist] (device_t &root, char const *type, bool first)
/*TODO*///			{
/*TODO*///				for (software_list_device &swlistdev : software_list_device_iterator(root))
/*TODO*///				{
/*TODO*///					if (core_strwildcmp(gamename, swlistdev.list_name().c_str()) == 0 && list_map.insert(swlistdev.list_name()).second)
/*TODO*///					{
/*TODO*///						if (!swlistdev.get_info().empty())
/*TODO*///						{
/*TODO*///							if (firstlist)
/*TODO*///							{
/*TODO*///								if (m_options.bool_value(CLIOPTION_DTD))
/*TODO*///									std::cout << s_softlist_xml_dtd;
/*TODO*///								std::cout << "<softwarelists>\n";
/*TODO*///								firstlist = false;
/*TODO*///							}
/*TODO*///							output_single_softlist(std::cout, swlistdev);
/*TODO*///						}
/*TODO*///					}
/*TODO*///				}
/*TODO*///			});
/*TODO*///
/*TODO*///	if (!firstlist)
/*TODO*///		std::cout << "</softwarelists>\n";
/*TODO*///	else
/*TODO*///		fprintf(stdout, "No such software lists found\n"); // TODO: should this go to stderr instead?
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*////*-------------------------------------------------
/*TODO*///    verifysoftlist - verify software list by name
/*TODO*///-------------------------------------------------*/
/*TODO*///void cli_frontend::verifysoftlist(const std::vector<std::string> &args)
/*TODO*///{
/*TODO*///	const char *gamename = args.empty() ? "*" : args[0].c_str();
/*TODO*///
/*TODO*///	std::unordered_set<std::string> list_map;
/*TODO*///	unsigned correct = 0;
/*TODO*///	unsigned incorrect = 0;
/*TODO*///	unsigned notfound = 0;
/*TODO*///	unsigned matched = 0;
/*TODO*///
/*TODO*///	driver_enumerator drivlist(m_options);
/*TODO*///	media_auditor auditor(drivlist);
/*TODO*///	util::ovectorstream summary_string;
/*TODO*///
/*TODO*///	while (drivlist.next())
/*TODO*///	{
/*TODO*///		for (software_list_device &swlistdev : software_list_device_iterator(drivlist.config()->root_device()))
/*TODO*///		{
/*TODO*///			if (core_strwildcmp(gamename, swlistdev.list_name().c_str()) == 0 && list_map.insert(swlistdev.list_name()).second)
/*TODO*///			{
/*TODO*///				if (!swlistdev.get_info().empty())
/*TODO*///				{
/*TODO*///					matched++;
/*TODO*///
/*TODO*///					// Get the actual software list contents
/*TODO*///					for (const software_info &swinfo : swlistdev.get_info())
/*TODO*///					{
/*TODO*///						media_auditor::summary summary = auditor.audit_software(swlistdev, swinfo, AUDIT_VALIDATE_FAST);
/*TODO*///
/*TODO*///						print_summary(
/*TODO*///								auditor, summary, false,
/*TODO*///								"rom", util::string_format("%s:%s", swlistdev.list_name(), swinfo.shortname()).c_str(), nullptr,
/*TODO*///								correct, incorrect, notfound,
/*TODO*///								summary_string);
/*TODO*///					}
/*TODO*///				}
/*TODO*///			}
/*TODO*///		}
/*TODO*///	}
/*TODO*///
/*TODO*///	// clear out any cached files
/*TODO*///	util::archive_file::cache_clear();
/*TODO*///
/*TODO*///	// return an error if none found
/*TODO*///	if (matched == 0)
/*TODO*///		throw emu_fatalerror(EMU_ERR_NO_SUCH_SYSTEM, "No matching software lists found for '%s'", gamename);
/*TODO*///
/*TODO*///	// if we didn't get anything at all, display a generic end message
/*TODO*///	if (matched > 0 && correct == 0 && incorrect == 0)
/*TODO*///	{
/*TODO*///		throw emu_fatalerror(EMU_ERR_MISSING_FILES, "no romsets found for software list \"%s\"!\n", gamename);
/*TODO*///	}
/*TODO*///	// otherwise, print a summary
/*TODO*///	else
/*TODO*///	{
/*TODO*///		if (incorrect > 0)
/*TODO*///			throw emu_fatalerror(EMU_ERR_MISSING_FILES, "%u romsets found in %u software lists, %u were OK.\n", correct + incorrect, matched, correct);
/*TODO*///		osd_printf_info("%u romsets found in %u software lists, %u romsets were OK.\n", correct, matched, correct);
/*TODO*///	}
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  version - emit MAME version to stdout
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///void cli_frontend::version(const std::vector<std::string> &args)
/*TODO*///{
/*TODO*///	osd_printf_info("%s", emulator_info::get_build_version());
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  romident - identify ROMs by looking for
/*TODO*/////  matches in our internal database
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///void cli_frontend::romident(const std::vector<std::string> &args)
/*TODO*///{
/*TODO*///	const char *filename = args[0].c_str();
/*TODO*///
/*TODO*///	// create our own copy of options for the purposes of ROM identification
/*TODO*///	// so we are not "polluted" with driver-specific slot/image options
/*TODO*///	emu_options options;
/*TODO*///	options.set_value(OPTION_MEDIAPATH, m_options.media_path(), OPTION_PRIORITY_DEFAULT);
/*TODO*///
/*TODO*///	media_identifier ident(options);
/*TODO*///
/*TODO*///	// identify the file, then output results
/*TODO*///	osd_printf_info("Identifying %s....\n", filename);
/*TODO*///	ident.identify(filename);
/*TODO*///
/*TODO*///	// return the appropriate error code
/*TODO*///	if (ident.total() == 0)
/*TODO*///		throw emu_fatalerror(EMU_ERR_MISSING_FILES, "No files found.\n");
/*TODO*///	else if (ident.matches() == ident.total())
/*TODO*///		return;
/*TODO*///	else if (ident.matches() == ident.total() - ident.nonroms())
/*TODO*///		throw emu_fatalerror(EMU_ERR_IDENT_NONROMS, "Out of %d files, %d matched, %d are not roms.\n", ident.total(), ident.matches(), ident.nonroms());
/*TODO*///	else if (ident.matches() > 0)
/*TODO*///		throw emu_fatalerror(EMU_ERR_IDENT_PARTIAL, "Out of %d files, %d matched, %d did not match.\n", ident.total(), ident.matches(), ident.total() - ident.matches());
/*TODO*///	else
/*TODO*///		throw emu_fatalerror(EMU_ERR_IDENT_NONE, "No roms matched.\n");
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  apply_action - apply action to matching
/*TODO*/////  systems/devices
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///template <typename T, typename U> void cli_frontend::apply_action(const std::vector<std::string> &args, T &&drvact, U &&devact)
/*TODO*///
/*TODO*///{
/*TODO*///	bool const iswild((1U != args.size()) || core_iswildstr(args[0].c_str()));
/*TODO*///	std::vector<bool> matched(args.size(), false);
/*TODO*///	auto const included = [&args, &matched] (char const *name) -> bool
/*TODO*///	{
/*TODO*///		if (args.empty())
/*TODO*///			return true;
/*TODO*///
/*TODO*///		bool result = false;
/*TODO*///		auto it = matched.begin();
/*TODO*///		for (std::string const &pat : args)
/*TODO*///		{
/*TODO*///			if (!core_strwildcmp(pat.c_str(), name))
/*TODO*///			{
/*TODO*///				result = true;
/*TODO*///				*it = true;
/*TODO*///			}
/*TODO*///			++it;
/*TODO*///		}
/*TODO*///		return result;
/*TODO*///	};
/*TODO*///
/*TODO*///	// determine which drivers to output
/*TODO*///	driver_enumerator drivlist(m_options);
/*TODO*///
/*TODO*///	// iterate through matches
/*TODO*///	bool first(true);
/*TODO*///	while (drivlist.next())
/*TODO*///	{
/*TODO*///		if (included(drivlist.driver().name))
/*TODO*///		{
/*TODO*///			drvact(drivlist, first);
/*TODO*///			first = false;
/*TODO*///
/*TODO*///			// if it wasn't a wildcard, there can only be one
/*TODO*///			if (!iswild)
/*TODO*///				break;
/*TODO*///		}
/*TODO*///	}
/*TODO*///
/*TODO*///	if (iswild || first)
/*TODO*///	{
/*TODO*///		for (device_type type : registered_device_types)
/*TODO*///		{
/*TODO*///			if (included(type.shortname()))
/*TODO*///			{
/*TODO*///				devact(type, first);
/*TODO*///				first = false;
/*TODO*///
/*TODO*///				// if it wasn't a wildcard, there can only be one
/*TODO*///				if (!iswild)
/*TODO*///					break;
/*TODO*///			}
/*TODO*///		}
/*TODO*///	}
/*TODO*///
/*TODO*///	// return an error if none found
/*TODO*///	auto it = matched.begin();
/*TODO*///	for (std::string const &pat : args)
/*TODO*///	{
/*TODO*///		if (!*it)
/*TODO*///			throw emu_fatalerror(EMU_ERR_NO_SUCH_SYSTEM, "No matching systems found for '%s'", pat.c_str());
/*TODO*///
/*TODO*///		++it;
/*TODO*///	}
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  apply_device_action - apply action to matching
/*TODO*/////  systems/devices
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///template <typename T> void cli_frontend::apply_device_action(const std::vector<std::string> &args, T &&action)
/*TODO*///{
/*TODO*///	machine_config config(GAME_NAME(___empty), m_options);
/*TODO*///	machine_config::token const tok(config.begin_configuration(config.root_device()));
/*TODO*///	apply_action(
/*TODO*///			args,
/*TODO*///			[&action] (driver_enumerator &drivlist, bool first)
/*TODO*///			{
/*TODO*///				action(drivlist.config()->root_device(), "driver", first);
/*TODO*///			},
/*TODO*///			[&action, &config] (device_type type, bool first)
/*TODO*///			{
/*TODO*///				device_t *const dev = config.device_add("_tmp", type, 0);
/*TODO*///				action(*dev, "device", first);
/*TODO*///				config.device_remove("_tmp");
/*TODO*///			});
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  find_command
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///const cli_frontend::info_command_struct *cli_frontend::find_command(const std::string &s)
/*TODO*///{
/*TODO*///	static const info_command_struct s_info_commands[] =
/*TODO*///	{
/*TODO*///		{ CLICOMMAND_LISTXML,           0, -1, &cli_frontend::listxml,          "[pattern] ..." },
/*TODO*///		{ CLICOMMAND_LISTFULL,          0, -1, &cli_frontend::listfull,         "[pattern] ..." },
/*TODO*///		{ CLICOMMAND_LISTSOURCE,        0, -1, &cli_frontend::listsource,       "[system name]" },
/*TODO*///		{ CLICOMMAND_LISTCLONES,        0,  1, &cli_frontend::listclones,       "[system name]" },
/*TODO*///		{ CLICOMMAND_LISTBROTHERS,      0,  1, &cli_frontend::listbrothers,     "[system name]" },
/*TODO*///		{ CLICOMMAND_LISTCRC,           0, -1, &cli_frontend::listcrc,          "[system name]" },
/*TODO*///		{ CLICOMMAND_LISTDEVICES,       0,  1, &cli_frontend::listdevices,      "[system name]" },
/*TODO*///		{ CLICOMMAND_LISTSLOTS,         0,  1, &cli_frontend::listslots,        "[system name]" },
/*TODO*///		{ CLICOMMAND_LISTROMS,          0, -1, &cli_frontend::listroms,         "[pattern] ..." },
/*TODO*///		{ CLICOMMAND_LISTSAMPLES,       0,  1, &cli_frontend::listsamples,      "[system name]" },
/*TODO*///		{ CLICOMMAND_VERIFYROMS,        0, -1, &cli_frontend::verifyroms,       "[pattern] ..." },
/*TODO*///		{ CLICOMMAND_VERIFYSAMPLES,     0,  1, &cli_frontend::verifysamples,    "[system name|*]" },
/*TODO*///		{ CLICOMMAND_LISTMEDIA,         0,  1, &cli_frontend::listmedia,        "[system name]" },
/*TODO*///		{ CLICOMMAND_LISTSOFTWARE,      0,  1, &cli_frontend::listsoftware,     "[system name]" },
/*TODO*///		{ CLICOMMAND_VERIFYSOFTWARE,    0,  1, &cli_frontend::verifysoftware,   "[system name|*]" },
/*TODO*///		{ CLICOMMAND_ROMIDENT,          1,  1, &cli_frontend::romident,         "(file or directory path)" },
/*TODO*///		{ CLICOMMAND_GETSOFTLIST,       0,  1, &cli_frontend::getsoftlist,      "[system name|*]" },
/*TODO*///		{ CLICOMMAND_VERIFYSOFTLIST,    0,  1, &cli_frontend::verifysoftlist,   "[system name|*]" },
/*TODO*///		{ CLICOMMAND_VERSION,           0,  0, &cli_frontend::version,          "" }
/*TODO*///	};
/*TODO*///
/*TODO*///	for (const auto &info_command : s_info_commands)
/*TODO*///	{
/*TODO*///		if (s == info_command.option)
/*TODO*///			return &info_command;
/*TODO*///	}
/*TODO*///	return nullptr;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  execute_commands - execute various frontend
/*TODO*/////  commands
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///void cli_frontend::execute_commands(const char *exename)
/*TODO*///{
/*TODO*///	// help?
/*TODO*///	if (m_options.command() == CLICOMMAND_HELP)
/*TODO*///	{
/*TODO*///		display_help(exename);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///
/*TODO*///	// showusage?
/*TODO*///	if (m_options.command() == CLICOMMAND_SHOWUSAGE)
/*TODO*///	{
/*TODO*///		osd_printf_info("Usage:  %s [machine] [media] [software] [options]",exename);
/*TODO*///		osd_printf_info("\n\nOptions:\n%s", m_options.output_help());
/*TODO*///		return;
/*TODO*///	}
/*TODO*///
/*TODO*///	// validate?
/*TODO*///	if (m_options.command() == CLICOMMAND_VALIDATE)
/*TODO*///	{
/*TODO*///		if (m_options.command_arguments().size() > 1)
/*TODO*///		{
/*TODO*///			osd_printf_error("Auxiliary verb -validate takes at most 1 argument\n");
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		validity_checker valid(m_options, false);
/*TODO*///		const char *sysname = m_options.command_arguments().empty() ? nullptr : m_options.command_arguments()[0].c_str();
/*TODO*///		bool result = valid.check_all_matching(sysname);
/*TODO*///		if (!result)
/*TODO*///			throw emu_fatalerror(EMU_ERR_FAILED_VALIDITY, "Validity check failed (%d errors, %d warnings in total)\n", valid.errors(), valid.warnings());
/*TODO*///		return;
/*TODO*///	}
/*TODO*///
/*TODO*///	// other commands need the INIs parsed
/*TODO*///	std::ostringstream option_errors;
/*TODO*///	mame_options::parse_standard_inis(m_options,option_errors);
/*TODO*///	if (option_errors.tellp() > 0)
/*TODO*///		osd_printf_error("%s\n", option_errors.str());
/*TODO*///
/*TODO*///	// createconfig?
/*TODO*///	if (m_options.command() == CLICOMMAND_CREATECONFIG)
/*TODO*///	{
/*TODO*///		// attempt to open the output file
/*TODO*///		emu_file file(OPEN_FLAG_WRITE | OPEN_FLAG_CREATE | OPEN_FLAG_CREATE_PATHS);
/*TODO*///		if (file.open(std::string(emulator_info::get_configname()) + ".ini") != osd_file::error::NONE)
/*TODO*///			throw emu_fatalerror("Unable to create file %s.ini\n",emulator_info::get_configname());
/*TODO*///
/*TODO*///		// generate the updated INI
/*TODO*///		file.puts(m_options.output_ini().c_str());
/*TODO*///
/*TODO*///		ui_options ui_opts;
/*TODO*///		emu_file file_ui(OPEN_FLAG_WRITE | OPEN_FLAG_CREATE | OPEN_FLAG_CREATE_PATHS);
/*TODO*///		if (file_ui.open("ui.ini") != osd_file::error::NONE)
/*TODO*///			throw emu_fatalerror("Unable to create file ui.ini\n");
/*TODO*///
/*TODO*///		// generate the updated INI
/*TODO*///		file_ui.puts(ui_opts.output_ini().c_str());
/*TODO*///
/*TODO*///		plugin_options plugin_opts;
/*TODO*///		path_iterator iter(m_options.plugins_path());
/*TODO*///		std::string pluginpath;
/*TODO*///		while (iter.next(pluginpath))
/*TODO*///		{
/*TODO*///			osd_subst_env(pluginpath, pluginpath);
/*TODO*///			plugin_opts.scan_directory(pluginpath, true);
/*TODO*///		}
/*TODO*///		emu_file file_plugin(OPEN_FLAG_WRITE | OPEN_FLAG_CREATE | OPEN_FLAG_CREATE_PATHS);
/*TODO*///		if (file_plugin.open("plugin.ini") != osd_file::error::NONE)
/*TODO*///			throw emu_fatalerror("Unable to create file plugin.ini\n");
/*TODO*///
/*TODO*///		// generate the updated INI
/*TODO*///		file_plugin.puts(plugin_opts.output_ini().c_str());
/*TODO*///
/*TODO*///		return;
/*TODO*///	}
/*TODO*///
/*TODO*///	// showconfig?
/*TODO*///	if (m_options.command() == CLICOMMAND_SHOWCONFIG)
/*TODO*///	{
/*TODO*///		// print the INI text
/*TODO*///		printf("%s\n", m_options.output_ini().c_str());
/*TODO*///		return;
/*TODO*///	}
/*TODO*///
/*TODO*///	// all other commands call out to one of the info_commands helpers; first
/*TODO*///	// find the command
/*TODO*///	const auto *info_command = find_command(m_options.command());
/*TODO*///	if (info_command)
/*TODO*///	{
/*TODO*///		// validate argument count
/*TODO*///		const char *error_message = nullptr;
/*TODO*///		if (m_options.command_arguments().size() < info_command->min_args)
/*TODO*///			error_message = "Auxiliary verb -%s requires at least %d argument(s)\n";
/*TODO*///		if ((info_command->max_args >= 0) && (m_options.command_arguments().size() > info_command->max_args))
/*TODO*///			error_message = "Auxiliary verb -%s takes at most %d argument(s)\n";
/*TODO*///		if (error_message)
/*TODO*///		{
/*TODO*///			osd_printf_info(error_message, info_command->option, info_command->max_args);
/*TODO*///			osd_printf_info("\n");
/*TODO*///			osd_printf_info("Usage:  %s -%s %s\n", exename, info_command->option, info_command->usage);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		// invoke the auxiliary command!
/*TODO*///		(this->*info_command->function)(m_options.command_arguments());
/*TODO*///		return;
/*TODO*///	}
/*TODO*///
/*TODO*///	if (!m_osd.execute_command(m_options.command().c_str()))
/*TODO*///		// if we get here, we don't know what has been requested
/*TODO*///		throw emu_fatalerror(EMU_ERR_INVALID_CONFIG, "Unknown command '%s' specified", m_options.command().c_str());
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  display_help - display help to standard
/*TODO*/////  output
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///void cli_frontend::display_help(const char *exename)
/*TODO*///{
/*TODO*///	osd_printf_info(
/*TODO*///			"%3$s v%2$s\n"
/*TODO*///			"%5$s\n"
/*TODO*///			"\n"
/*TODO*///			"This software reproduces, more or less faithfully, the behaviour of a wide range\n"
/*TODO*///			"of machines. But hardware is useless without software, so images of the ROMs and\n"
/*TODO*///			"other media which run on that hardware are also required.\n"
/*TODO*///			"\n"
/*TODO*///			"Usage:  %1$s [machine] [media] [software] [options]\n"
/*TODO*///			"\n"
/*TODO*///			"        %1$s -showusage    for a list of options\n"
/*TODO*///			"        %1$s -showconfig   to show current configuration in %4$s.ini format\n"
/*TODO*///			"        %1$s -listmedia    for a full list of supported media\n"
/*TODO*///			"        %1$s -createconfig to create a %4$s.ini file\n"
/*TODO*///			"\n"
/*TODO*///			"For usage instructions, please visit https://docs.mamedev.org/\n",
/*TODO*///			exename,
/*TODO*///			build_version,
/*TODO*///			emulator_info::get_appname(),
/*TODO*///			emulator_info::get_configname(),
/*TODO*///			emulator_info::get_copyright_info());
/*TODO*///}
    
}
