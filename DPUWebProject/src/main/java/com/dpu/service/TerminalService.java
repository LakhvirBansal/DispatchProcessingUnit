package com.dpu.service;

import java.util.List;

import com.dpu.model.TerminalResponse;

public interface TerminalService {

	
	Object addTerminal(TerminalResponse terminalResponse);

	Object deleteTerminal(Long id);

	List<TerminalResponse> getAllTerminals();

	TerminalResponse getTerminal(Long id);

	TerminalResponse getOpenAdd();

	Object updateTerminal(Long id, TerminalResponse terminalResponse);

	List<TerminalResponse> getTerminalByTerminalName(String terminalName);
	
}
