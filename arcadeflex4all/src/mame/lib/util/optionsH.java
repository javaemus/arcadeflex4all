package mame.lib.util;

public class optionsH {
/*TODO*///// license:BSD-3-Clause
/*TODO*///// copyright-holders:Aaron Giles
/*TODO*////***************************************************************************
/*TODO*///
/*TODO*///    options.h
/*TODO*///
/*TODO*///    Core options code code
/*TODO*///
/*TODO*///***************************************************************************/
/*TODO*///
/*TODO*///#ifndef MAME_LIB_UTIL_OPTIONS_H
/*TODO*///#define MAME_LIB_UTIL_OPTIONS_H
/*TODO*///
/*TODO*///#include "corefile.h"
/*TODO*///#include <unordered_map>
/*TODO*///#include <sstream>
/*TODO*///
/*TODO*///
/*TODO*/////**************************************************************************
/*TODO*/////  CONSTANTS
/*TODO*/////**************************************************************************
/*TODO*///
/*TODO*///// option priorities
/*TODO*///const int OPTION_PRIORITY_DEFAULT   = 0;            // defaults are at 0 priority
/*TODO*///const int OPTION_PRIORITY_LOW       = 50;           // low priority
/*TODO*///const int OPTION_PRIORITY_NORMAL    = 100;          // normal priority
/*TODO*///const int OPTION_PRIORITY_HIGH      = 150;          // high priority
/*TODO*///const int OPTION_PRIORITY_MAXIMUM   = 255;          // maximum priority
/*TODO*///
/*TODO*///
/*TODO*/////**************************************************************************
/*TODO*/////  TYPE DEFINITIONS
/*TODO*/////**************************************************************************
/*TODO*///
/*TODO*///struct options_entry;
/*TODO*///
/*TODO*///// exception thrown by core_options when an illegal request is made
/*TODO*///class options_exception : public std::exception
/*TODO*///{
/*TODO*///public:
/*TODO*///	const std::string &message() const { return m_message; }
/*TODO*///	virtual const char *what() const noexcept override { return message().c_str(); }
/*TODO*///
/*TODO*///protected:
/*TODO*///	options_exception(std::string &&message);
/*TODO*///
/*TODO*///private:
/*TODO*///	std::string m_message;
/*TODO*///};
/*TODO*///
/*TODO*///class options_warning_exception : public options_exception
/*TODO*///{
/*TODO*///public:
/*TODO*///	template <typename... Params> options_warning_exception(const char *fmt, Params &&...args)
/*TODO*///		: options_warning_exception(util::string_format(fmt, std::forward<Params>(args)...))
/*TODO*///	{
/*TODO*///	}
/*TODO*///
/*TODO*///	options_warning_exception(std::string &&message);
/*TODO*///	options_warning_exception(const options_warning_exception &) = default;
/*TODO*///	options_warning_exception(options_warning_exception &&) = default;
/*TODO*///	options_warning_exception& operator=(const options_warning_exception &) = default;
/*TODO*///	options_warning_exception& operator=(options_warning_exception &&) = default;
/*TODO*///};
/*TODO*///
/*TODO*///class options_error_exception : public options_exception
/*TODO*///{
/*TODO*///public:
/*TODO*///	template <typename... Params> options_error_exception(const char *fmt, Params &&...args)
/*TODO*///		: options_error_exception(util::string_format(fmt, std::forward<Params>(args)...))
/*TODO*///	{
/*TODO*///	}
/*TODO*///
/*TODO*///	options_error_exception(std::string &&message);
/*TODO*///	options_error_exception(const options_error_exception &) = default;
/*TODO*///	options_error_exception(options_error_exception &&) = default;
/*TODO*///	options_error_exception& operator=(const options_error_exception &) = default;
/*TODO*///	options_error_exception& operator=(options_error_exception &&) = default;
/*TODO*///};
/*TODO*///
/*TODO*///
/*TODO*///// structure holding information about a collection of options
/*TODO*///class core_options
/*TODO*///{
/*TODO*///	static const int MAX_UNADORNED_OPTIONS = 16;
/*TODO*///
/*TODO*///public:
    public static enum option_type
    {
		INVALID,         // invalid
		HEADER,          // a header item
		COMMAND,         // a command
		BOOLEAN,         // boolean option
		INTEGER,         // integer option
		FLOAT,           // floating-point option
		STRING           // string option
    };

/*TODO*///	// information about a single entry in the options
/*TODO*///	class entry
/*TODO*///	{
/*TODO*///	public:
/*TODO*///		typedef std::shared_ptr<entry> shared_ptr;
/*TODO*///		typedef std::shared_ptr<const entry> shared_const_ptr;
/*TODO*///		typedef std::weak_ptr<entry> weak_ptr;
/*TODO*///
/*TODO*///		// constructor/destructor
/*TODO*///		entry(std::vector<std::string> &&names, option_type type = option_type::STRING, const char *description = nullptr);
/*TODO*///		entry(std::string &&name, option_type type = option_type::STRING, const char *description = nullptr);
/*TODO*///		entry(const entry &) = delete;
/*TODO*///		entry(entry &&) = delete;
/*TODO*///		entry& operator=(const entry &) = delete;
/*TODO*///		entry& operator=(entry &&) = delete;
/*TODO*///		virtual ~entry();
/*TODO*///
/*TODO*///		// accessors
/*TODO*///		const std::vector<std::string> &names() const noexcept { return m_names; }
/*TODO*///		const std::string &name() const noexcept { return m_names[0]; }
/*TODO*///		virtual const char *value() const noexcept;
/*TODO*///		int priority() const noexcept { return m_priority; }
/*TODO*///		void set_priority(int priority) noexcept { m_priority = priority; }
/*TODO*///		option_type type() const noexcept { return m_type; }
/*TODO*///		const char *description() const noexcept { return m_description; }
/*TODO*///		virtual const std::string &default_value() const noexcept;
/*TODO*///		virtual const char *minimum() const noexcept;
/*TODO*///		virtual const char *maximum() const noexcept;
/*TODO*///		bool has_range() const noexcept;
/*TODO*///
/*TODO*///		// mutators
/*TODO*///		void set_value(std::string &&newvalue, int priority, bool always_override = false);
/*TODO*///		virtual void set_default_value(std::string &&newvalue);
/*TODO*///		void set_description(const char *description) { m_description = description; }
/*TODO*///		void set_value_changed_handler(std::function<void(const char *)> &&handler) { m_value_changed_handler = std::move(handler); }
/*TODO*///		virtual void revert(int priority_hi, int priority_lo) { }
/*TODO*///
/*TODO*///	protected:
/*TODO*///		virtual void internal_set_value(std::string &&newvalue) = 0;
/*TODO*///
/*TODO*///	private:
/*TODO*///		void validate(const std::string &value);
/*TODO*///
/*TODO*///		std::vector<std::string>                    m_names;
/*TODO*///		int                                         m_priority;
/*TODO*///		core_options::option_type                   m_type;
/*TODO*///		const char *                                m_description;
/*TODO*///		std::function<void(const char *)>           m_value_changed_handler;
/*TODO*///	};
/*TODO*///
/*TODO*///	// construction/destruction
/*TODO*///	core_options();
/*TODO*///	core_options(const core_options &) = delete;
/*TODO*///	core_options(core_options &&) = default;
/*TODO*///	core_options& operator=(const core_options &) = delete;
/*TODO*///	core_options& operator=(core_options &&) = default;
/*TODO*///	virtual ~core_options();
/*TODO*///
/*TODO*///	// getters
/*TODO*///	const std::string &command() const noexcept { return m_command; }
/*TODO*///	const std::vector<std::string> &command_arguments() const noexcept { assert(!m_command.empty()); return m_command_arguments; }
/*TODO*///	entry::shared_const_ptr get_entry(const std::string &name) const noexcept;
/*TODO*///	entry::shared_ptr get_entry(const std::string &name) noexcept;
/*TODO*///	const std::vector<entry::shared_ptr> &entries() const noexcept { return m_entries; }
/*TODO*///	bool exists(const std::string &name) const noexcept { return get_entry(name) != nullptr; }
/*TODO*///	bool header_exists(const char *description) const noexcept;
/*TODO*///
/*TODO*///	// configuration
/*TODO*///	void add_entry(entry::shared_ptr &&entry, const char *after_header = nullptr);
/*TODO*///	void add_entry(const options_entry &entry, bool override_existing = false);
/*TODO*///	void add_entry(std::vector<std::string> &&names, const char *description, option_type type, std::string &&default_value = "", std::string &&minimum = "", std::string &&maximum = "");
/*TODO*///	void add_header(const char *description);
/*TODO*///	void add_entries(const options_entry *entrylist, bool override_existing = false);
/*TODO*///	void set_default_value(const char *name, const char *defvalue);
/*TODO*///	void set_description(const char *name, const char *description);
/*TODO*///	void remove_entry(entry &delentry);
/*TODO*///	void set_value_changed_handler(const std::string &name, std::function<void(const char *)> &&handler);
/*TODO*///	void revert(int priority_hi = OPTION_PRIORITY_MAXIMUM, int priority_lo = OPTION_PRIORITY_DEFAULT);
/*TODO*///
/*TODO*///	// parsing/input
/*TODO*///	void parse_command_line(const std::vector<std::string> &args, int priority, bool ignore_unknown_options = false);
/*TODO*///	void parse_ini_file(util::core_file &inifile, int priority, bool ignore_unknown_options, bool always_override);
/*TODO*///	void copy_from(const core_options &that);
/*TODO*///
/*TODO*///	// output
/*TODO*///	std::string output_ini(const core_options *diff = nullptr) const;
/*TODO*///	std::string output_help() const;
/*TODO*///
/*TODO*///	// reading
/*TODO*///	const char *value(const char *option) const noexcept;
/*TODO*///	const char *description(const char *option) const noexcept;
/*TODO*///	bool bool_value(const char *option) const { return int_value(option) != 0; }
/*TODO*///	int int_value(const char *option) const;
/*TODO*///	float float_value(const char *option) const;
/*TODO*///
/*TODO*///	// setting
/*TODO*///	void set_value(const std::string &name, const std::string &value, int priority);
/*TODO*///	void set_value(const std::string &name, std::string &&value, int priority);
/*TODO*///	void set_value(const std::string &name, int value, int priority);
/*TODO*///	void set_value(const std::string &name, float value, int priority);
/*TODO*///
/*TODO*///	// misc
/*TODO*///	static const char *unadorned(int x = 0) noexcept { return s_option_unadorned[std::min(x, MAX_UNADORNED_OPTIONS - 1)]; }
/*TODO*///
/*TODO*///protected:
/*TODO*///	virtual void command_argument_processed() { }
/*TODO*///
/*TODO*///private:
/*TODO*///	class simple_entry : public entry
/*TODO*///	{
/*TODO*///	public:
/*TODO*///		// construction/destruction
/*TODO*///		simple_entry(std::vector<std::string> &&names, const char *description, core_options::option_type type, std::string &&defdata, std::string &&minimum, std::string &&maximum);
/*TODO*///		simple_entry(const simple_entry &) = delete;
/*TODO*///		simple_entry(simple_entry &&) = delete;
/*TODO*///		simple_entry& operator=(const simple_entry &) = delete;
/*TODO*///		simple_entry& operator=(simple_entry &&) = delete;
/*TODO*///		~simple_entry();
/*TODO*///
/*TODO*///		// getters
/*TODO*///		virtual const char *value() const noexcept override;
/*TODO*///		virtual const char *minimum() const noexcept override;
/*TODO*///		virtual const char *maximum() const noexcept override;
/*TODO*///		virtual const std::string &default_value() const noexcept override;
/*TODO*///		virtual void revert(int priority_hi, int priority_lo) override;
/*TODO*///
/*TODO*///		virtual void set_default_value(std::string &&newvalue) override;
/*TODO*///
/*TODO*///	protected:
/*TODO*///		virtual void internal_set_value(std::string &&newvalue) override;
/*TODO*///
/*TODO*///	private:
/*TODO*///		// internal state
/*TODO*///		std::string             m_data;             // data for this item
/*TODO*///		std::string             m_defdata;          // default data for this item
/*TODO*///		std::string             m_minimum;          // minimum value
/*TODO*///		std::string             m_maximum;          // maximum value
/*TODO*///	};
/*TODO*///
/*TODO*///	// used internally in core_options
/*TODO*///	enum class condition_type
/*TODO*///	{
/*TODO*///		NONE,
/*TODO*///		WARN,
/*TODO*///		ERR
/*TODO*///	};
/*TODO*///
/*TODO*///	// internal helpers
/*TODO*///	void add_to_entry_map(std::string &&name, entry::shared_ptr &entry);
/*TODO*///	void do_set_value(entry &curentry, std::string &&data, int priority, std::ostream &error_stream, condition_type &condition);
/*TODO*///	void throw_options_exception_if_appropriate(condition_type condition, std::ostringstream &error_stream);
/*TODO*///
/*TODO*///	// internal state
/*TODO*///	std::vector<entry::shared_ptr>                      m_entries;              // canonical list of entries
/*TODO*///	std::unordered_map<std::string, entry::weak_ptr>    m_entrymap;             // map for fast lookup
/*TODO*///	std::string                                         m_command;              // command found
/*TODO*///	std::vector<std::string>                            m_command_arguments;    // command arguments
/*TODO*///	static const char *const                            s_option_unadorned[];   // array of unadorned option "names"
/*TODO*///};
/*TODO*///

    // static structure describing a single option with its description and default value
    public class options_entry
    {
        public String                name;               // name on the command line
        public String                defvalue;           // default value of this argument
        public option_type           type;               // type of option
        public String                description;        // description for -showusage
    };

/*TODO*///// legacy option types
/*TODO*///const core_options::option_type OPTION_INVALID = core_options::option_type::INVALID;
/*TODO*///const core_options::option_type OPTION_HEADER = core_options::option_type::HEADER;
/*TODO*///const core_options::option_type OPTION_COMMAND = core_options::option_type::COMMAND;
/*TODO*///const core_options::option_type OPTION_BOOLEAN = core_options::option_type::BOOLEAN;
/*TODO*///const core_options::option_type OPTION_INTEGER = core_options::option_type::INTEGER;
/*TODO*///const core_options::option_type OPTION_FLOAT = core_options::option_type::FLOAT;
/*TODO*///const core_options::option_type OPTION_STRING = core_options::option_type::STRING;
/*TODO*///
/*TODO*///
/*TODO*///#endif // MAME_LIB_UTIL_OPTIONS_H
    
}
