
package mame.frotend.mame;

public class clifrontH {
/*TODO*///// license:BSD-3-Clause
/*TODO*///// copyright-holders:Aaron Giles
/*TODO*////***************************************************************************
/*TODO*///
/*TODO*///    clifront.h
/*TODO*///
/*TODO*///    Command-line interface frontend for MAME.
/*TODO*///
/*TODO*///***************************************************************************/
/*TODO*///#ifndef MAME_FRONTEND_CLIFRONT_H
/*TODO*///#define MAME_FRONTEND_CLIFRONT_H
/*TODO*///
/*TODO*///#pragma once
/*TODO*///
/*TODO*///#include "emuopts.h"
/*TODO*///
/*TODO*///// don't include osd_interface in header files
/*TODO*///class osd_interface;
/*TODO*///class mame_machine_manager;
/*TODO*///
/*TODO*/////**************************************************************************
/*TODO*/////  TYPE DEFINITIONS
/*TODO*/////**************************************************************************
/*TODO*///
/*TODO*///
/*TODO*///// cli_frontend handles command-line processing and emulator execution
/*TODO*///class cli_frontend
/*TODO*///{
/*TODO*///	static const char s_softlist_xml_dtd[];
/*TODO*///
/*TODO*///public:
/*TODO*///	// construction/destruction
/*TODO*///	cli_frontend(emu_options &options, osd_interface &osd);
/*TODO*///	~cli_frontend();
/*TODO*///
/*TODO*///	// execute based on the incoming argc/argv
/*TODO*///	int execute(std::vector<std::string> &args);
/*TODO*///
/*TODO*///private:
/*TODO*///	struct info_command_struct
/*TODO*///	{
/*TODO*///		const char *option;
/*TODO*///		int min_args;
/*TODO*///		int max_args;
/*TODO*///		void (cli_frontend::*function)(const std::vector<std::string> &args);
/*TODO*///		const char *usage;
/*TODO*///	};
/*TODO*///
/*TODO*///	// commands
/*TODO*///	void listxml(const std::vector<std::string> &args);
/*TODO*///	void listfull(const std::vector<std::string> &args);
/*TODO*///	void listsource(const std::vector<std::string> &args);
/*TODO*///	void listclones(const std::vector<std::string> &args);
/*TODO*///	void listbrothers(const std::vector<std::string> &args);
/*TODO*///	void listcrc(const std::vector<std::string> &args);
/*TODO*///	void listroms(const std::vector<std::string> &args);
/*TODO*///	void listsamples(const std::vector<std::string> &args);
/*TODO*///	void listdevices(const std::vector<std::string> &args);
/*TODO*///	void listslots(const std::vector<std::string> &args);
/*TODO*///	void listmedia(const std::vector<std::string> &args);
/*TODO*///	void listsoftware(const std::vector<std::string> &args);
/*TODO*///	void verifysoftware(const std::vector<std::string> &args);
/*TODO*///	void verifyroms(const std::vector<std::string> &args);
/*TODO*///	void verifysamples(const std::vector<std::string> &args);
/*TODO*///	void romident(const std::vector<std::string> &args);
/*TODO*///	void getsoftlist(const std::vector<std::string> &args);
/*TODO*///	void verifysoftlist(const std::vector<std::string> &args);
/*TODO*///	void version(const std::vector<std::string> &args);
/*TODO*///
/*TODO*///	// internal helpers
/*TODO*///	template <typename T, typename U> void apply_action(const std::vector<std::string> &args, T &&drvact, U &&devact);
/*TODO*///	template <typename T> void apply_device_action(const std::vector<std::string> &args, T &&action);
/*TODO*///	void execute_commands(const char *exename);
/*TODO*///	void display_help(const char *exename);
/*TODO*///	void output_single_softlist(std::ostream &out, software_list_device &swlist);
/*TODO*///	void start_execution(mame_machine_manager *manager, const std::vector<std::string> &args);
/*TODO*///	static const info_command_struct *find_command(const std::string &s);
/*TODO*///
/*TODO*///	// internal state
/*TODO*///	emu_options &       m_options;
/*TODO*///	osd_interface &     m_osd;
/*TODO*///	int                 m_result;
/*TODO*///};
/*TODO*///
/*TODO*///#endif  // MAME_FRONTEND_CLIFRONT_H
    
}
