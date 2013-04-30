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
package ru.appliedtech.storage.hibernate;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;

import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

public class DDLGeneratorUtil {

	public static final String MYSQL_DIALECT_CLASSNAME = "org.hibernate.dialect.MySQL5InnoDBDialect";
	public static final String MYSQL_DIALECT_CLASSNAME2 = "ru.appliedtech.storage.hibernate.MySQLInnoDBCaseSensitiveDialect";
	public static final String API_ENTITIES_PACKAGE_NAME = "com.intel.esg.apiportal.engine.services.hibernate.beans";
	public static final String PORTAL_ENTITIES_PACKAGE_NAME = "com.intel.esg.apiportal.web.services.hibernate.beans";
	
	public static void execute(String dialectClassName, String packageName, String outputFilePath) {
		Configuration configuration = new Configuration();
		configuration.addPackage(packageName);
		configuration.setProperty(Environment.DIALECT, dialectClassName);
		Collection<Class<? extends Object>> classes = getPackageClasses(packageName);
		for (Class<?> entityClass : classes) {
			configuration.addAnnotatedClass(entityClass);
		}
		SchemaExport schemaExport = new SchemaExport(configuration);
		schemaExport.setDelimiter(";");
		schemaExport.setOutputFile(outputFilePath);
		schemaExport.create(true, false);
	}
	
	  private static Set<Class<? extends Object>> getPackageClasses(String packageName){
		  
		  List<ClassLoader> classLoadersList = new LinkedList<ClassLoader>();
		  classLoadersList.add(ClasspathHelper.contextClassLoader());
		  classLoadersList.add(ClasspathHelper.staticClassLoader());
		  ClasspathHelper.forPackage(packageName, classLoadersList.get(0));

		  Reflections reflections = new Reflections(new ConfigurationBuilder()
		      .setScanners(new SubTypesScanner(false), new ResourcesScanner(), new TypeAnnotationsScanner())
		      .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[2])))
		      .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(packageName))));

		  Set<Class<? extends Object>> allClasses = reflections.getTypesAnnotatedWith(Entity.class);
		  return allClasses;
	  }
}
