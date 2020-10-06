package mame.lib.util;

public class options {
/*TODO*///// license:BSD-3-Clause
/*TODO*///// copyright-holders:Aaron Giles
/*TODO*////***************************************************************************
/*TODO*///
/*TODO*///    options.cpp
/*TODO*///
/*TODO*///    Core options code code
/*TODO*///
/*TODO*///***************************************************************************/
/*TODO*///
/*TODO*///#include "options.h"
/*TODO*///
/*TODO*///#include "corestr.h"
/*TODO*///
/*TODO*///#include <locale>
/*TODO*///#include <string>
/*TODO*///
/*TODO*///#include <cassert>
/*TODO*///#include <cctype>
/*TODO*///#include <cstdarg>
/*TODO*///#include <cstdlib>
/*TODO*///
/*TODO*///
/*TODO*///const int core_options::MAX_UNADORNED_OPTIONS;
/*TODO*///
/*TODO*/////**************************************************************************
/*TODO*/////  GLOBAL VARIABLES
/*TODO*/////**************************************************************************
/*TODO*///
/*TODO*///const char *const core_options::s_option_unadorned[MAX_UNADORNED_OPTIONS] =
/*TODO*///{
/*TODO*///	"<UNADORNED0>",
/*TODO*///	"<UNADORNED1>",
/*TODO*///	"<UNADORNED2>",
/*TODO*///	"<UNADORNED3>",
/*TODO*///	"<UNADORNED4>",
/*TODO*///	"<UNADORNED5>",
/*TODO*///	"<UNADORNED6>",
/*TODO*///	"<UNADORNED7>",
/*TODO*///	"<UNADORNED8>",
/*TODO*///	"<UNADORNED9>",
/*TODO*///	"<UNADORNED10>",
/*TODO*///	"<UNADORNED11>",
/*TODO*///	"<UNADORNED12>",
/*TODO*///	"<UNADORNED13>",
/*TODO*///	"<UNADORNED14>",
/*TODO*///	"<UNADORNED15>"
/*TODO*///};
/*TODO*///
/*TODO*///
/*TODO*/////**************************************************************************
/*TODO*/////  UTILITY
/*TODO*/////**************************************************************************
/*TODO*///
/*TODO*///namespace
/*TODO*///{
/*TODO*///	void trim_spaces_and_quotes(std::string &data)
/*TODO*///	{
/*TODO*///		// trim any whitespace
/*TODO*///		strtrimspace(data);
/*TODO*///
/*TODO*///		// trim quotes
/*TODO*///		if (data.find_first_of('"') == 0 && data.find_last_of('"') == data.length() - 1)
/*TODO*///		{
/*TODO*///			data.erase(0, 1);
/*TODO*///			data.erase(data.length() - 1, 1);
/*TODO*///		}
/*TODO*///	}
/*TODO*///};
/*TODO*///
/*TODO*/////**************************************************************************
/*TODO*/////  OPTIONS EXCEPTION CLASS
/*TODO*/////**************************************************************************
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  options_exception - constructor
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///options_exception::options_exception(std::string &&message)
/*TODO*///	: m_message(std::move(message))
/*TODO*///{
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  options_warning_exception - constructor
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///options_warning_exception::options_warning_exception(std::string &&message)
/*TODO*///	: options_exception(std::move(message))
/*TODO*///{
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  options_error_exception - constructor
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///options_error_exception::options_error_exception(std::string &&message)
/*TODO*///	: options_exception(std::move(message))
/*TODO*///{
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////**************************************************************************
/*TODO*/////  CORE OPTIONS ENTRY BASE CLASS
/*TODO*/////**************************************************************************
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  entry - constructor
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///core_options::entry::entry(std::vector<std::string> &&names, core_options::option_type type, const char *description)
/*TODO*///	: m_names(std::move(names))
/*TODO*///	, m_priority(OPTION_PRIORITY_DEFAULT)
/*TODO*///	, m_type(type)
/*TODO*///	, m_description(description)
/*TODO*///{
/*TODO*///	assert(m_names.empty() == (m_type == option_type::HEADER));
/*TODO*///}
/*TODO*///
/*TODO*///core_options::entry::entry(std::string &&name, core_options::option_type type, const char *description)
/*TODO*///	: entry(std::vector<std::string>({ std::move(name) }), type, description)
/*TODO*///{
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  entry - destructor
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///core_options::entry::~entry()
/*TODO*///{
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  entry::value
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///const char *core_options::entry::value() const noexcept
/*TODO*///{
/*TODO*///	// returning 'nullptr' from here signifies a value entry that is essentially "write only"
/*TODO*///	// and cannot be meaningfully persisted (e.g. - a command or the software name)
/*TODO*///	return nullptr;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  entry::set_value
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///void core_options::entry::set_value(std::string &&newvalue, int priority_value, bool always_override)
/*TODO*///{
/*TODO*///	// it is invalid to set the value on a header
/*TODO*///	assert(type() != option_type::HEADER);
/*TODO*///
/*TODO*///	// only set the value if we have priority
/*TODO*///	if (always_override || priority_value >= priority())
/*TODO*///	{
/*TODO*///		internal_set_value(std::move(newvalue));
/*TODO*///		m_priority = priority_value;
/*TODO*///
/*TODO*///		// invoke the value changed handler, if appropriate
/*TODO*///		if (m_value_changed_handler)
/*TODO*///			m_value_changed_handler(value());
/*TODO*///	}
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  entry::set_default_value
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///void core_options::entry::set_default_value(std::string &&newvalue)
/*TODO*///{
/*TODO*///	// set_default_value() is not necessarily supported for all entry types
/*TODO*///	throw false;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  entry::validate
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///void core_options::entry::validate(const std::string &data)
/*TODO*///{
/*TODO*///	std::istringstream str(data);
/*TODO*///	str.imbue(std::locale::classic());
/*TODO*///
/*TODO*///	switch (type())
/*TODO*///	{
/*TODO*///	case option_type::BOOLEAN:
/*TODO*///		{
/*TODO*///			// booleans must be 0 or 1
/*TODO*///			int ival;
/*TODO*///			if (!(str >> ival) || (0 > ival) || (1 < ival))
/*TODO*///				throw options_warning_exception("Illegal boolean value for %s: \"%s\"; reverting to %s\n", name(), data, value());
/*TODO*///		}
/*TODO*///		break;
/*TODO*///
/*TODO*///	case option_type::INTEGER:
/*TODO*///		{
/*TODO*///			// integers must be integral
/*TODO*///			int ival;
/*TODO*///			if (!(str >> ival))
/*TODO*///				throw options_warning_exception("Illegal integer value for %s: \"%s\"; reverting to %s\n", name(), data, value());
/*TODO*///
/*TODO*///			// range checking
/*TODO*///			char const *const strmin(minimum());
/*TODO*///			char const *const strmax(maximum());
/*TODO*///			int imin(0), imax(0);
/*TODO*///			if (strmin)
/*TODO*///			{
/*TODO*///				str.str(strmin);
/*TODO*///				str >> imin;
/*TODO*///			}
/*TODO*///			if (strmax)
/*TODO*///			{
/*TODO*///				str.str(strmax);
/*TODO*///				str >> imax;
/*TODO*///			}
/*TODO*///			if ((strmin && (ival < imin)) || (strmax && (ival > imax)))
/*TODO*///			{
/*TODO*///				if (!strmax)
/*TODO*///					throw options_warning_exception("Out-of-range integer value for %s: \"%s\" (must be no less than %d); reverting to %s\n", name(), data, imin, value());
/*TODO*///				else if (!strmin)
/*TODO*///					throw options_warning_exception("Out-of-range integer value for %s: \"%s\" (must be no greater than %d); reverting to %s\n", name(), data, imax, value());
/*TODO*///				else
/*TODO*///					throw options_warning_exception("Out-of-range integer value for %s: \"%s\" (must be between %d and %d, inclusive); reverting to %s\n", name(), data, imin, imax, value());
/*TODO*///			}
/*TODO*///		}
/*TODO*///		break;
/*TODO*///
/*TODO*///	case option_type::FLOAT:
/*TODO*///		{
/*TODO*///			float fval;
/*TODO*///			if (!(str >> fval))
/*TODO*///				throw options_warning_exception("Illegal float value for %s: \"%s\"; reverting to %s\n", name(), data, value());
/*TODO*///
/*TODO*///			// range checking
/*TODO*///			char const *const strmin(minimum());
/*TODO*///			char const *const strmax(maximum());
/*TODO*///			float fmin(0), fmax(0);
/*TODO*///			if (strmin)
/*TODO*///			{
/*TODO*///				str.str(strmin);
/*TODO*///				str >> fmin;
/*TODO*///			}
/*TODO*///			if (strmax)
/*TODO*///			{
/*TODO*///				str.str(strmax);
/*TODO*///				str >> fmax;
/*TODO*///			}
/*TODO*///			if ((strmin && (fval < fmin)) || (strmax && (fval > fmax)))
/*TODO*///			{
/*TODO*///				if (!strmax)
/*TODO*///					throw options_warning_exception("Out-of-range float value for %s: \"%s\" (must be no less than %f); reverting to %s\n", name(), data, fmin, value());
/*TODO*///				else if (!strmin)
/*TODO*///					throw options_warning_exception("Out-of-range float value for %s: \"%s\" (must be no greater than %f); reverting to %s\n", name(), data, fmax, value());
/*TODO*///				else
/*TODO*///					throw options_warning_exception("Out-of-range float value for %s: \"%s\" (must be between %f and %f, inclusive); reverting to %s\n", name(), data, fmin, fmax, value());
/*TODO*///			}
/*TODO*///		}
/*TODO*///		break;
/*TODO*///
/*TODO*///	case OPTION_STRING:
/*TODO*///		// strings can be anything
/*TODO*///		break;
/*TODO*///
/*TODO*///	case OPTION_INVALID:
/*TODO*///	case OPTION_HEADER:
/*TODO*///	default:
/*TODO*///		// anything else is invalid
/*TODO*///		throw options_error_exception("Attempted to set invalid option %s\n", name());
/*TODO*///	}
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  entry::minimum
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///const char *core_options::entry::minimum() const noexcept
/*TODO*///{
/*TODO*///	return nullptr;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  entry::maximum
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///const char *core_options::entry::maximum() const noexcept
/*TODO*///{
/*TODO*///	return nullptr;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  entry::has_range
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///bool core_options::entry::has_range() const noexcept
/*TODO*///{
/*TODO*///	return minimum() && maximum();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  entry::default_value
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///const std::string &core_options::entry::default_value() const noexcept
/*TODO*///{
/*TODO*///	// I don't really want this generally available, but MewUI seems to need it.  Please do not use.
/*TODO*///	abort();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////**************************************************************************
/*TODO*/////  CORE OPTIONS SIMPLE ENTRYCLASS
/*TODO*/////**************************************************************************
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  simple_entry - constructor
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///core_options::simple_entry::simple_entry(std::vector<std::string> &&names, const char *description, core_options::option_type type, std::string &&defdata, std::string &&minimum, std::string &&maximum)
/*TODO*///	: entry(std::move(names), type, description)
/*TODO*///	, m_defdata(std::move(defdata))
/*TODO*///	, m_minimum(std::move(minimum))
/*TODO*///	, m_maximum(std::move(maximum))
/*TODO*///{
/*TODO*///	m_data = m_defdata;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  simple_entry - destructor
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///core_options::simple_entry::~simple_entry()
/*TODO*///{
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  simple_entry::value
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///const char *core_options::simple_entry::value() const noexcept
/*TODO*///{
/*TODO*///	switch (type())
/*TODO*///	{
/*TODO*///	case core_options::option_type::BOOLEAN:
/*TODO*///	case core_options::option_type::INTEGER:
/*TODO*///	case core_options::option_type::FLOAT:
/*TODO*///	case core_options::option_type::STRING:
/*TODO*///		return m_data.c_str();
/*TODO*///
/*TODO*///	default:
/*TODO*///		// this is an option type for which returning a value is
/*TODO*///		// a meaningless operation (e.g. - core_options::option_type::COMMAND)
/*TODO*///		return nullptr;
/*TODO*///	}
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  simple_entry::default_value
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///const std::string &core_options::simple_entry::default_value() const noexcept
/*TODO*///{
/*TODO*///	// only MewUI seems to need this; please don't use
/*TODO*///	return m_defdata;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  internal_set_value
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///void core_options::simple_entry::internal_set_value(std::string &&newvalue)
/*TODO*///{
/*TODO*///	m_data = std::move(newvalue);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  set_default_value
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///void core_options::simple_entry::set_default_value(std::string &&newvalue)
/*TODO*///{
/*TODO*///	m_data = m_defdata = std::move(newvalue);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  minimum
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///const char *core_options::simple_entry::minimum() const noexcept
/*TODO*///{
/*TODO*///	return m_minimum.c_str();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  maximum
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///const char *core_options::simple_entry::maximum() const noexcept
/*TODO*///{
/*TODO*///	return m_maximum.c_str();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////**************************************************************************
/*TODO*/////  CORE OPTIONS
/*TODO*/////**************************************************************************
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  core_options - constructor
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///core_options::core_options()
/*TODO*///{
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  ~core_options - destructor
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///core_options::~core_options()
/*TODO*///{
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  add_entry - adds an entry
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///void core_options::add_entry(entry::shared_ptr &&entry, const char *after_header)
/*TODO*///{
/*TODO*///	// update the entry map
/*TODO*///	for (const std::string &name : entry->names())
/*TODO*///	{
/*TODO*///		// append the entry
/*TODO*///		add_to_entry_map(std::string(name), entry);
/*TODO*///
/*TODO*///		// for booleans, add the "-noXYZ" option as well
/*TODO*///		if (entry->type() == option_type::BOOLEAN)
/*TODO*///			add_to_entry_map(std::string("no") + name, entry);
/*TODO*///	}
/*TODO*///
/*TODO*///	// and add the entry to the vector
/*TODO*///	m_entries.emplace_back(std::move(entry));
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  add_to_entry_map - adds an entry to the entry
/*TODO*/////  map
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///void core_options::add_to_entry_map(std::string &&name, entry::shared_ptr &entry)
/*TODO*///{
/*TODO*///	// it is illegal to call this method for something that already exists
/*TODO*///	assert(m_entrymap.find(name) == m_entrymap.end());
/*TODO*///
/*TODO*///	// append the entry
/*TODO*///	m_entrymap.emplace(std::make_pair(name, entry::weak_ptr(entry)));
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  add_entry - adds an entry based on an
/*TODO*/////  options_entry
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///void core_options::add_entry(const options_entry &opt, bool override_existing)
/*TODO*///{
/*TODO*///	std::vector<std::string> names;
/*TODO*///	std::string minimum, maximum;
/*TODO*///
/*TODO*///	// copy in the name(s) as appropriate
/*TODO*///	if (opt.name)
/*TODO*///	{
/*TODO*///		// first extract any range
/*TODO*///		std::string namestr(opt.name);
/*TODO*///		int lparen = namestr.find_first_of('(', 0);
/*TODO*///		int dash = namestr.find_first_of('-', lparen + 1);
/*TODO*///		int rparen = namestr.find_first_of(')', dash + 1);
/*TODO*///		if (lparen != -1 && dash != -1 && rparen != -1)
/*TODO*///		{
/*TODO*///			strtrimspace(minimum.assign(namestr.substr(lparen + 1, dash - (lparen + 1))));
/*TODO*///			strtrimspace(maximum.assign(namestr.substr(dash + 1, rparen - (dash + 1))));
/*TODO*///			namestr.erase(lparen, rparen + 1 - lparen);
/*TODO*///		}
/*TODO*///
/*TODO*///		// then chop up any semicolon-separated names
/*TODO*///		size_t semi;
/*TODO*///		while ((semi = namestr.find_first_of(';')) != std::string::npos)
/*TODO*///		{
/*TODO*///			names.push_back(namestr.substr(0, semi));
/*TODO*///			namestr.erase(0, semi + 1);
/*TODO*///		}
/*TODO*///
/*TODO*///		// finally add the last item
/*TODO*///		names.push_back(std::move(namestr));
/*TODO*///	}
/*TODO*///
/*TODO*///	// we might be called with an existing entry
/*TODO*///	entry::shared_ptr existing_entry;
/*TODO*///	do
/*TODO*///	{
/*TODO*///		for (const std::string &name : names)
/*TODO*///		{
/*TODO*///			existing_entry = get_entry(name);
/*TODO*///			if (existing_entry)
/*TODO*///				break;
/*TODO*///		}
/*TODO*///
/*TODO*///		if (existing_entry)
/*TODO*///		{
/*TODO*///			if (override_existing)
/*TODO*///				remove_entry(*existing_entry);
/*TODO*///			else
/*TODO*///				return;
/*TODO*///		}
/*TODO*///	} while (existing_entry);
/*TODO*///
/*TODO*///	// set the default value
/*TODO*///	std::string defdata = opt.defvalue ? opt.defvalue : "";
/*TODO*///
/*TODO*///	// create and add the entry
/*TODO*///	add_entry(
/*TODO*///			std::move(names),
/*TODO*///			opt.description,
/*TODO*///			opt.type,
/*TODO*///			std::move(defdata),
/*TODO*///			std::move(minimum),
/*TODO*///			std::move(maximum));
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  add_entry
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///void core_options::add_entry(std::vector<std::string> &&names, const char *description, option_type type, std::string &&default_value, std::string &&minimum, std::string &&maximum)
/*TODO*///{
/*TODO*///	// create the entry
/*TODO*///	entry::shared_ptr new_entry = std::make_shared<simple_entry>(
/*TODO*///			std::move(names),
/*TODO*///			description,
/*TODO*///			type,
/*TODO*///			std::move(default_value),
/*TODO*///			std::move(minimum),
/*TODO*///			std::move(maximum));
/*TODO*///
/*TODO*///	// and add it
/*TODO*///	add_entry(std::move(new_entry));
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  add_header
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///void core_options::add_header(const char *description)
/*TODO*///{
/*TODO*///	add_entry(std::vector<std::string>(), description, option_type::HEADER);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  add_entries - add entries to the current
/*TODO*/////  options sets
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///void core_options::add_entries(const options_entry *entrylist, bool override_existing)
/*TODO*///{
/*TODO*///	// loop over entries until we hit a nullptr name
/*TODO*///	for (int i = 0; entrylist[i].name || entrylist[i].type == option_type::HEADER; i++)
/*TODO*///		add_entry(entrylist[i], override_existing);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  set_default_value - change the default value
/*TODO*/////  of an option
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///void core_options::set_default_value(const char *name, const char *defvalue)
/*TODO*///{
/*TODO*///	// update the data and default data
/*TODO*///	get_entry(name)->set_default_value(defvalue);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  set_description - change the description
/*TODO*/////  of an option
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///void core_options::set_description(const char *name, const char *description)
/*TODO*///{
/*TODO*///	// update the data and default data
/*TODO*///	get_entry(name)->set_description(description);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  parse_command_line - parse a series of
/*TODO*/////  command line arguments
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///void core_options::parse_command_line(const std::vector<std::string> &args, int priority, bool ignore_unknown_options)
/*TODO*///{
/*TODO*///	std::ostringstream error_stream;
/*TODO*///	condition_type condition = condition_type::NONE;
/*TODO*///
/*TODO*///	// reset the errors and the command
/*TODO*///	m_command.clear();
/*TODO*///
/*TODO*///	// we want to identify commands first
/*TODO*///	for (size_t arg = 1; arg < args.size(); arg++)
/*TODO*///	{
/*TODO*///		if (!args[arg].empty() && args[arg][0] == '-')
/*TODO*///		{
/*TODO*///			auto curentry = get_entry(&args[arg][1]);
/*TODO*///			if (curentry && curentry->type() == OPTION_COMMAND)
/*TODO*///			{
/*TODO*///				// can only have one command
/*TODO*///				if (!m_command.empty())
/*TODO*///					throw options_error_exception("Error: multiple commands specified -%s and %s\n", m_command, args[arg]);
/*TODO*///
/*TODO*///				m_command = curentry->name();
/*TODO*///			}
/*TODO*///		}
/*TODO*///	}
/*TODO*///
/*TODO*///	// iterate through arguments
/*TODO*///	int unadorned_index = 0;
/*TODO*///	for (size_t arg = 1; arg < args.size(); arg++)
/*TODO*///	{
/*TODO*///		// determine the entry name to search for
/*TODO*///		const char *curarg = args[arg].c_str();
/*TODO*///		bool is_unadorned = (curarg[0] != '-');
/*TODO*///		const char *optionname = is_unadorned ? core_options::unadorned(unadorned_index++) : &curarg[1];
/*TODO*///
/*TODO*///		// special case - collect unadorned arguments after commands into a special place
/*TODO*///		if (is_unadorned && !m_command.empty())
/*TODO*///		{
/*TODO*///			m_command_arguments.push_back(args[arg]);
/*TODO*///			command_argument_processed();
/*TODO*///			continue;
/*TODO*///		}
/*TODO*///
/*TODO*///		// find our entry; if not found, continue
/*TODO*///		auto curentry = get_entry(optionname);
/*TODO*///		if (!curentry)
/*TODO*///		{
/*TODO*///			if (!ignore_unknown_options)
/*TODO*///				throw options_error_exception("Error: unknown option: -%s\n", optionname);
/*TODO*///			continue;
/*TODO*///		}
/*TODO*///
/*TODO*///		// at this point, we've already processed commands
/*TODO*///		if (curentry->type() == OPTION_COMMAND)
/*TODO*///			continue;
/*TODO*///
/*TODO*///		// get the data for this argument, special casing booleans
/*TODO*///		std::string newdata;
/*TODO*///		if (curentry->type() == option_type::BOOLEAN)
/*TODO*///		{
/*TODO*///			newdata = (strncmp(&curarg[1], "no", 2) == 0) ? "0" : "1";
/*TODO*///		}
/*TODO*///		else if (is_unadorned)
/*TODO*///		{
/*TODO*///			newdata = curarg;
/*TODO*///		}
/*TODO*///		else if (arg + 1 < args.size())
/*TODO*///		{
/*TODO*///			newdata = args[++arg];
/*TODO*///		}
/*TODO*///		else
/*TODO*///		{
/*TODO*///			throw options_error_exception("Error: option %s expected a parameter\n", curarg);
/*TODO*///		}
/*TODO*///
/*TODO*///		// set the new data
/*TODO*///		do_set_value(*curentry, std::move(newdata), priority, error_stream, condition);
/*TODO*///	}
/*TODO*///
/*TODO*///	// did we have any errors that may need to be aggregated?
/*TODO*///	throw_options_exception_if_appropriate(condition, error_stream);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  parse_ini_file - parse a series of entries in
/*TODO*/////  an INI file
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///void core_options::parse_ini_file(util::core_file &inifile, int priority, bool ignore_unknown_options, bool always_override)
/*TODO*///{
/*TODO*///	std::ostringstream error_stream;
/*TODO*///	condition_type condition = condition_type::NONE;
/*TODO*///
/*TODO*///	// loop over lines in the file
/*TODO*///	char buffer[4096];
/*TODO*///	while (inifile.gets(buffer, ARRAY_LENGTH(buffer)) != nullptr)
/*TODO*///	{
/*TODO*///		// find the extent of the name
/*TODO*///		char *optionname;
/*TODO*///		for (optionname = buffer; *optionname != 0; optionname++)
/*TODO*///			if (!isspace((uint8_t)*optionname))
/*TODO*///				break;
/*TODO*///
/*TODO*///		// skip comments
/*TODO*///		if (*optionname == 0 || *optionname == '#')
/*TODO*///			continue;
/*TODO*///
/*TODO*///		// scan forward to find the first space
/*TODO*///		char *temp;
/*TODO*///		for (temp = optionname; *temp != 0; temp++)
/*TODO*///			if (isspace((uint8_t)*temp))
/*TODO*///				break;
/*TODO*///
/*TODO*///		// if we hit the end early, print a warning and continue
/*TODO*///		if (*temp == 0)
/*TODO*///		{
/*TODO*///			condition = std::max(condition, condition_type::WARN);
/*TODO*///			util::stream_format(error_stream, "Warning: invalid line in INI: %s", buffer);
/*TODO*///			continue;
/*TODO*///		}
/*TODO*///
/*TODO*///		// NULL-terminate
/*TODO*///		*temp++ = 0;
/*TODO*///		char *optiondata = temp;
/*TODO*///
/*TODO*///		// scan the data, stopping when we hit a comment
/*TODO*///		bool inquotes = false;
/*TODO*///		for (temp = optiondata; *temp != 0; temp++)
/*TODO*///		{
/*TODO*///			if (*temp == '"')
/*TODO*///				inquotes = !inquotes;
/*TODO*///			if (*temp == '#' && !inquotes)
/*TODO*///				break;
/*TODO*///		}
/*TODO*///		*temp = 0;
/*TODO*///
/*TODO*///		// find our entry
/*TODO*///		entry::shared_ptr curentry = get_entry(optionname);
/*TODO*///		if (!curentry)
/*TODO*///		{
/*TODO*///			if (!ignore_unknown_options)
/*TODO*///			{
/*TODO*///				condition = std::max(condition, condition_type::WARN);
/*TODO*///				util::stream_format(error_stream, "Warning: unknown option in INI: %s\n", optionname);
/*TODO*///			}
/*TODO*///			continue;
/*TODO*///		}
/*TODO*///
/*TODO*///		// set the new data
/*TODO*///		std::string data = optiondata;
/*TODO*///		trim_spaces_and_quotes(data);
/*TODO*///		do_set_value(*curentry, std::move(data), priority, error_stream, condition);
/*TODO*///	}
/*TODO*///
/*TODO*///	// did we have any errors that may need to be aggregated?
/*TODO*///	throw_options_exception_if_appropriate(condition, error_stream);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  throw_options_exception_if_appropriate
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///void core_options::throw_options_exception_if_appropriate(core_options::condition_type condition, std::ostringstream &error_stream)
/*TODO*///{
/*TODO*///	switch(condition)
/*TODO*///	{
/*TODO*///	case condition_type::NONE:
/*TODO*///		// do nothing
/*TODO*///		break;
/*TODO*///
/*TODO*///	case condition_type::WARN:
/*TODO*///		throw options_warning_exception(error_stream.str());
/*TODO*///
/*TODO*///	case condition_type::ERR:
/*TODO*///		throw options_error_exception(error_stream.str());
/*TODO*///
/*TODO*///	default:
/*TODO*///		// should not get here
/*TODO*///		throw false;
/*TODO*///	}
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  copy_from
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///void core_options::copy_from(const core_options &that)
/*TODO*///{
/*TODO*///	for (auto &dest_entry : m_entries)
/*TODO*///	{
/*TODO*///		if (dest_entry->names().size() > 0)
/*TODO*///		{
/*TODO*///			// identify the source entry
/*TODO*///			const entry::shared_const_ptr source_entry = that.get_entry(dest_entry->name());
/*TODO*///			if (source_entry)
/*TODO*///			{
/*TODO*///				const char *value = source_entry->value();
/*TODO*///				if (value)
/*TODO*///					dest_entry->set_value(value, source_entry->priority(), true);
/*TODO*///			}
/*TODO*///		}
/*TODO*///	}
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  output_ini - output the options in INI format,
/*TODO*/////  only outputting entries that different from
/*TODO*/////  the optional diff
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///std::string core_options::output_ini(const core_options *diff) const
/*TODO*///{
/*TODO*///	// INI files are complete, so always start with a blank buffer
/*TODO*///	std::ostringstream buffer;
/*TODO*///	buffer.imbue(std::locale::classic());
/*TODO*///
/*TODO*///	int num_valid_headers = 0;
/*TODO*///	int unadorned_index = 0;
/*TODO*///	const char *last_header = nullptr;
/*TODO*///	std::string overridden_value;
/*TODO*///
/*TODO*///	// loop over all items
/*TODO*///	for (auto &curentry : m_entries)
/*TODO*///	{
/*TODO*///		if (curentry->type() == option_type::HEADER)
/*TODO*///		{
/*TODO*///			// header: record description
/*TODO*///			last_header = curentry->description();
/*TODO*///		}
/*TODO*///		else
/*TODO*///		{
/*TODO*///			const std::string &name(curentry->name());
/*TODO*///			const char *value(curentry->value());
/*TODO*///
/*TODO*///			// check if it's unadorned
/*TODO*///			bool is_unadorned = false;
/*TODO*///			if (name == core_options::unadorned(unadorned_index))
/*TODO*///			{
/*TODO*///				unadorned_index++;
/*TODO*///				is_unadorned = true;
/*TODO*///			}
/*TODO*///
/*TODO*///			// output entries for all non-command items (items with value)
/*TODO*///			if (value)
/*TODO*///			{
/*TODO*///				// look up counterpart in diff, if diff is specified
/*TODO*///				if (!diff || strcmp(value, diff->value(name.c_str())))
/*TODO*///				{
/*TODO*///					// output header, if we have one
/*TODO*///					if (last_header)
/*TODO*///					{
/*TODO*///						if (num_valid_headers++)
/*TODO*///							buffer << '\n';
/*TODO*///						util::stream_format(buffer, "#\n# %s\n#\n", last_header);
/*TODO*///						last_header = nullptr;
/*TODO*///					}
/*TODO*///
/*TODO*///					// and finally output the data, skip if unadorned
/*TODO*///					if (!is_unadorned)
/*TODO*///					{
/*TODO*///						if (strchr(value, ' '))
/*TODO*///							util::stream_format(buffer, "%-25s \"%s\"\n", name, value);
/*TODO*///						else
/*TODO*///							util::stream_format(buffer, "%-25s %s\n", name, value);
/*TODO*///					}
/*TODO*///				}
/*TODO*///			}
/*TODO*///		}
/*TODO*///	}
/*TODO*///	return buffer.str();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  output_help - output option help to a string
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///std::string core_options::output_help() const
/*TODO*///{
/*TODO*///	// start empty
/*TODO*///	std::ostringstream buffer;
/*TODO*///
/*TODO*///	// loop over all items
/*TODO*///	for (auto &curentry : m_entries)
/*TODO*///	{
/*TODO*///		// header: just print
/*TODO*///		if (curentry->type() == option_type::HEADER)
/*TODO*///			util::stream_format(buffer, "\n#\n# %s\n#\n", curentry->description());
/*TODO*///
/*TODO*///		// otherwise, output entries for all non-deprecated items
/*TODO*///		else if (curentry->description() != nullptr)
/*TODO*///			util::stream_format(buffer, "-%-20s%s\n", curentry->name(), curentry->description());
/*TODO*///	}
/*TODO*///	return buffer.str();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  value - return the raw option value
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///const char *core_options::value(const char *option) const noexcept
/*TODO*///{
/*TODO*///	auto const entry = get_entry(option);
/*TODO*///	return entry ? entry->value() : nullptr;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  description - return description of option
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///const char *core_options::description(const char *option) const noexcept
/*TODO*///{
/*TODO*///	auto const entry = get_entry(option);
/*TODO*///	return entry ? entry->description() : nullptr;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  value - return the option value as an integer
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///int core_options::int_value(const char *option) const
/*TODO*///{
/*TODO*///	char const *const data = value(option);
/*TODO*///	if (!data)
/*TODO*///		return 0;
/*TODO*///	std::istringstream str(data);
/*TODO*///	str.imbue(std::locale::classic());
/*TODO*///	int ival;
/*TODO*///	if (str >> ival)
/*TODO*///		return ival;
/*TODO*///	else
/*TODO*///		return 0;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  value - return the option value as a float
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///float core_options::float_value(const char *option) const
/*TODO*///{
/*TODO*///	char const *const data = value(option);
/*TODO*///	if (!data)
/*TODO*///		return 0.0f;
/*TODO*///	std::istringstream str(data);
/*TODO*///	str.imbue(std::locale::classic());
/*TODO*///	float fval;
/*TODO*///	if (str >> fval)
/*TODO*///		return fval;
/*TODO*///	else
/*TODO*///		return 0.0f;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////**************************************************************************
/*TODO*/////  LEGACY
/*TODO*/////**************************************************************************
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  set_value - set the raw option value
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///void core_options::set_value(const std::string &name, const std::string &value, int priority)
/*TODO*///{
/*TODO*///	set_value(name, std::string(value), priority);
/*TODO*///}
/*TODO*///
/*TODO*///void core_options::set_value(const std::string &name, std::string &&value, int priority)
/*TODO*///{
/*TODO*///	get_entry(name)->set_value(std::move(value), priority);
/*TODO*///}
/*TODO*///
/*TODO*///void core_options::set_value(const std::string &name, int value, int priority)
/*TODO*///{
/*TODO*///	set_value(name, string_format("%d", value), priority);
/*TODO*///}
/*TODO*///
/*TODO*///void core_options::set_value(const std::string &name, float value, int priority)
/*TODO*///{
/*TODO*///	set_value(name, string_format("%f", value), priority);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  remove_entry - remove an entry from our list
/*TODO*/////  and map
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///void core_options::remove_entry(core_options::entry &delentry)
/*TODO*///{
/*TODO*///	// find this in m_entries
/*TODO*///	auto iter = std::find_if(
/*TODO*///			m_entries.begin(),
/*TODO*///			m_entries.end(),
/*TODO*///			[&delentry](const auto &x) { return &*x == &delentry; });
/*TODO*///	assert(iter != m_entries.end());
/*TODO*///
/*TODO*///	// erase each of the items out of the entry map
/*TODO*///	for (const std::string &name : delentry.names())
/*TODO*///		m_entrymap.erase(name);
/*TODO*///
/*TODO*///	// finally erase it
/*TODO*///	m_entries.erase(iter);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  do_set_value
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///void core_options::do_set_value(entry &curentry, std::string &&data, int priority, std::ostream &error_stream, condition_type &condition)
/*TODO*///{
/*TODO*///	// this is called when parsing a command line or an INI - we want to catch the option_exception and write
/*TODO*///	// any exception messages to the error stream
/*TODO*///	try
/*TODO*///	{
/*TODO*///		curentry.set_value(std::move(data), priority);
/*TODO*///	}
/*TODO*///	catch (options_warning_exception &ex)
/*TODO*///	{
/*TODO*///		// we want to aggregate option exceptions
/*TODO*///		error_stream << ex.message();
/*TODO*///		condition = std::max(condition, condition_type::WARN);
/*TODO*///	}
/*TODO*///	catch (options_error_exception &ex)
/*TODO*///	{
/*TODO*///		// we want to aggregate option exceptions
/*TODO*///		error_stream << ex.message();
/*TODO*///		condition = std::max(condition, condition_type::ERR);
/*TODO*///	}
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  get_entry
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///core_options::entry::shared_const_ptr core_options::get_entry(const std::string &name) const noexcept
/*TODO*///{
/*TODO*///	auto curentry = m_entrymap.find(name);
/*TODO*///	return (curentry != m_entrymap.end()) ? curentry->second.lock() : nullptr;
/*TODO*///}
/*TODO*///
/*TODO*///core_options::entry::shared_ptr core_options::get_entry(const std::string &name) noexcept
/*TODO*///{
/*TODO*///	auto curentry = m_entrymap.find(name);
/*TODO*///	return (curentry != m_entrymap.end()) ? curentry->second.lock() : nullptr;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  set_value_changed_handler
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///void core_options::set_value_changed_handler(const std::string &name, std::function<void(const char *)> &&handler)
/*TODO*///{
/*TODO*///	get_entry(name)->set_value_changed_handler(std::move(handler));
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  header_exists
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///bool core_options::header_exists(const char *description) const noexcept
/*TODO*///{
/*TODO*///	auto iter = std::find_if(
/*TODO*///			m_entries.begin(),
/*TODO*///			m_entries.end(),
/*TODO*///			[description](const auto &entry)
/*TODO*///			{
/*TODO*///				return entry->type() == option_type::HEADER
/*TODO*///						&& entry->description()
/*TODO*///						&& !strcmp(entry->description(), description);
/*TODO*///			});
/*TODO*///
/*TODO*///	return iter != m_entries.end();
/*TODO*///}
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  revert - revert options at or below a certain
/*TODO*/////  priority back to their defaults
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///void core_options::revert(int priority_hi, int priority_lo)
/*TODO*///{
/*TODO*///	for (entry::shared_ptr &curentry : m_entries)
/*TODO*///		if (curentry->type() != option_type::HEADER)
/*TODO*///			curentry->revert(priority_hi, priority_lo);
/*TODO*///}
/*TODO*///
/*TODO*/////-------------------------------------------------
/*TODO*/////  revert - revert back to our default if we are
/*TODO*/////  within the given priority range
/*TODO*/////-------------------------------------------------
/*TODO*///
/*TODO*///void core_options::simple_entry::revert(int priority_hi, int priority_lo)
/*TODO*///{
/*TODO*///	// if our priority is within the range, revert to the default
/*TODO*///	if (priority() <= priority_hi && priority() >= priority_lo)
/*TODO*///	{
/*TODO*///		set_value(std::string(default_value()), priority(), true);
/*TODO*///		set_priority(OPTION_PRIORITY_DEFAULT);
/*TODO*///	}
/*TODO*///}
    
}
