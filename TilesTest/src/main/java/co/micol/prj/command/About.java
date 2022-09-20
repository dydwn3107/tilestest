package co.micol.prj.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.micol.prj.common.Command;

public class About implements Command {

	@Override
	public String exec(HttpServletRequest request, HttpServletResponse response) {
		// about
		
		return "main/about";
	}

}
