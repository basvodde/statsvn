/*
    StatCvs - CVS statistics generation 
    Copyright (C) 2002  Lukasz Pekacki <lukasz@pekacki.de>
    http://statcvs.sf.net/
    
    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
    
	$RCSfile: NoFilesPage.java,v $ 
	Created on $Date: 2003/12/13 22:27:35 $ 
*/

package net.sf.statsvn.output;

import java.io.IOException;

import net.sf.statsvn.model.Repository;

/**
 * Generated if and only if there are no files in the repository
 * @author Richard Cyganiak
 */
public class NoFilesPage extends HTMLPage {

	/**
	 * @see net.sf.statsvn.output.HTMLPage#HTMLPage(Repository)
	 */
	public NoFilesPage(final Repository content, final OutputRenderer renderer) throws IOException {
		super(content, renderer);
		setFileName("index" + renderer.getFileExtension());
		setPageName("Development statistics for " + ConfigurationOptions.getProjectName());
		createPage();
	}

	protected void printBody() throws IOException {
		printParagraph("No files in repository");
	}
}