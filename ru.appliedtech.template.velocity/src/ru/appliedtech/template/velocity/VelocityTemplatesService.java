/*
* Copyright (c) 2013 Applied Technologies, Ltd.
*
* Permission is hereby granted, free of charge, to any person obtaining
* a copy of this software and associated documentation files (the
* "Software"), to deal in the Software without restriction, including
* without limitation the rights to use, copy, modify, merge, publish,
* distribute, sublicense, and/or sell copies of the Software, and to
* permit persons to whom the Software is furnished to do so, subject to
* the following conditions:
*
* The above copyright notice and this permission notice shall be
* included in all copies or substantial portions of the Software.
*
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
* EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
* MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
* NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
* LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
* OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
* WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*
*/
package ru.appliedtech.template.velocity;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.VelocityException;
import org.apache.velocity.tools.ToolManager;
import org.apache.velocity.tools.config.EasyFactoryConfiguration;
import org.apache.velocity.tools.generic.DateTool;
import org.apache.velocity.tools.generic.EscapeTool;
import org.apache.velocity.tools.generic.NumberTool;
import org.omg.CORBA.portable.ApplicationException;

import ru.appliedtech.application.AppException;
import ru.appliedtech.application.ApplicationStatus;
import ru.appliedtech.template.ITemplatesService;

/**
 * The Velocity implementaion of the {@link ITemplatesService}.
 * @author Igor Pavlenko
 */
public class VelocityTemplatesService implements ITemplatesService {
	private ToolManager toolManager;

	/**
	 * Constructor.
	 * @param config The config.
	 * @throws ApplicationException
	 */
	public VelocityTemplatesService(VelocityTemplatesConfig config) throws AppException {
		try {
			Properties props = new Properties();
			props.setProperty("velocimacro.library", "");
			props.setProperty("resource.loader", "custom");
			props.setProperty("custom.resource.loader.description", "Custom Velocity Resource Loader");
			props.setProperty("custom.resource.loader.class", VelocityResourceLoader.class.getName());
			props.setProperty("custom.resource.loader.cache", String.valueOf(config.cache));
			props.setProperty("custom.resource.loader.modificationCheckInterval", String.valueOf(config.modificationCheckInterval));

			Velocity.init(props);

			toolManager = new ToolManager();

			EasyFactoryConfiguration efc = new EasyFactoryConfiguration();
			efc.toolbox("application").tool("number", NumberTool.class).tool("date", DateTool.class).tool("esc", EscapeTool.class);
			toolManager.configure(efc);
		} catch (VelocityException e) {
			throw new AppException(e, ApplicationStatus.FAILURE_UNKNOWN_REASON);
		}
	}

	@Override
	public String process(String name, Map<String, ?> params) throws AppException {
		try {
			VelocityContext context = new VelocityContext(params, toolManager.createContext());
			Template template = Velocity.getTemplate(name);

			Writer writer = null;
			try {
				writer = new StringWriter();
				template.merge(context, writer);
				return writer.toString();
			} finally {
				if (writer != null) {
					writer.close();
				}
			}
		} catch (VelocityException e) {
			throw new AppException(e, ApplicationStatus.FAILURE_UNKNOWN_REASON);
		} catch (IOException e) {
			throw new AppException(e, ApplicationStatus.FAILURE_UNKNOWN_REASON);
		}
	}
}
