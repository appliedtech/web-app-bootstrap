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
package ru.appliedtech.application.utils;

import java.util.HashMap;
import java.util.Map;

import lombok.val;

public class MimeTypes {

	public static final String MIME_APPLICATION_ANDREW_INSET = "application/andrew-inset";
	public static final String MIME_APPLICATION_JSON = "application/json";
	public static final String MIME_APPLICATION_ZIP = "application/zip";
	public static final String MIME_APPLICATION_X_GZIP = "application/x-gzip";
	public static final String MIME_APPLICATION_TGZ = "application/tgz";
	public static final String MIME_APPLICATION_MSWORD = "application/msword";
	public static final String MIME_APPLICATION_POSTSCRIPT = "application/postscript";
	public static final String MIME_APPLICATION_PDF = "application/pdf";
	public static final String MIME_APPLICATION_JNLP = "application/jnlp";
	public static final String MIME_APPLICATION_MAC_BINHEX40 = "application/mac-binhex40";
	public static final String MIME_APPLICATION_MAC_COMPACTPRO = "application/mac-compactpro";
	public static final String MIME_APPLICATION_MATHML_XML = "application/mathml+xml";
	public static final String MIME_APPLICATION_OCTET_STREAM = "application/octet-stream";
	public static final String MIME_APPLICATION_ODA = "application/oda";
	public static final String MIME_APPLICATION_RDF_XML = "application/rdf+xml";
	public static final String MIME_APPLICATION_JAVA_ARCHIVE = "application/java-archive";
	public static final String MIME_APPLICATION_RDF_SMIL = "application/smil";
	public static final String MIME_APPLICATION_SRGS = "application/srgs";
	public static final String MIME_APPLICATION_SRGS_XML = "application/srgs+xml";
	public static final String MIME_APPLICATION_VND_MIF = "application/vnd.mif";
	public static final String MIME_APPLICATION_VND_MSEXCEL = "application/vnd.ms-excel";
	public static final String MIME_APPLICATION_VND_MSPOWERPOINT = "application/vnd.ms-powerpoint";
	public static final String MIME_APPLICATION_VND_RNREALMEDIA = "application/vnd.rn-realmedia";
	public static final String MIME_APPLICATION_X_BCPIO = "application/x-bcpio";
	public static final String MIME_APPLICATION_X_CDLINK = "application/x-cdlink";
	public static final String MIME_APPLICATION_X_CHESS_PGN = "application/x-chess-pgn";
	public static final String MIME_APPLICATION_X_CPIO = "application/x-cpio";
	public static final String MIME_APPLICATION_X_CSH = "application/x-csh";
	public static final String MIME_APPLICATION_X_DIRECTOR = "application/x-director";
	public static final String MIME_APPLICATION_X_DVI = "application/x-dvi";
	public static final String MIME_APPLICATION_X_FUTURESPLASH = "application/x-futuresplash";
	public static final String MIME_APPLICATION_X_GTAR = "application/x-gtar";
	public static final String MIME_APPLICATION_X_HDF = "application/x-hdf";
	public static final String MIME_APPLICATION_X_JAVASCRIPT = "application/x-javascript";
	public static final String MIME_APPLICATION_X_KOAN = "application/x-koan";
	public static final String MIME_APPLICATION_X_LATEX = "application/x-latex";
	public static final String MIME_APPLICATION_X_NETCDF = "application/x-netcdf";
	public static final String MIME_APPLICATION_X_OGG = "application/x-ogg";
	public static final String MIME_APPLICATION_X_SH = "application/x-sh";
	public static final String MIME_APPLICATION_X_SHAR = "application/x-shar";
	public static final String MIME_APPLICATION_X_SHOCKWAVE_FLASH = "application/x-shockwave-flash";
	public static final String MIME_APPLICATION_X_STUFFIT = "application/x-stuffit";
	public static final String MIME_APPLICATION_X_SV4CPIO = "application/x-sv4cpio";
	public static final String MIME_APPLICATION_X_SV4CRC = "application/x-sv4crc";
	public static final String MIME_APPLICATION_X_TAR = "application/x-tar";
	public static final String MIME_APPLICATION_X_RAR_COMPRESSED = "application/x-rar-compressed";
	public static final String MIME_APPLICATION_X_TCL = "application/x-tcl";
	public static final String MIME_APPLICATION_X_TEX = "application/x-tex";
	public static final String MIME_APPLICATION_X_TEXINFO = "application/x-texinfo";
	public static final String MIME_APPLICATION_X_TROFF = "application/x-troff";
	public static final String MIME_APPLICATION_X_TROFF_MAN = "application/x-troff-man";
	public static final String MIME_APPLICATION_X_TROFF_ME = "application/x-troff-me";
	public static final String MIME_APPLICATION_X_TROFF_MS = "application/x-troff-ms";
	public static final String MIME_APPLICATION_X_USTAR = "application/x-ustar";
	public static final String MIME_APPLICATION_X_WAIS_SOURCE = "application/x-wais-source";
	public static final String MIME_APPLICATION_VND_MOZZILLA_XUL_XML = "application/vnd.mozilla.xul+xml";
	public static final String MIME_APPLICATION_XHTML_XML = "application/xhtml+xml";
	public static final String MIME_APPLICATION_XSLT_XML = "application/xslt+xml";
	public static final String MIME_APPLICATION_XML = "application/xml";
	public static final String MIME_APPLICATION_XML_DTD = "application/xml-dtd";
	public static final String MIME_IMAGE_BMP = "image/bmp";
	public static final String MIME_IMAGE_CGM = "image/cgm";
	public static final String MIME_IMAGE_GIF = "image/gif";
	public static final String MIME_IMAGE_IEF = "image/ief";
	public static final String MIME_IMAGE_JPEG = "image/jpeg";
	public static final String MIME_IMAGE_TIFF = "image/tiff";
	public static final String MIME_IMAGE_PNG = "image/png";
	public static final String MIME_IMAGE_SVG_XML = "image/svg+xml";
	public static final String MIME_IMAGE_VND_DJVU = "image/vnd.djvu";
	public static final String MIME_IMAGE_WAP_WBMP = "image/vnd.wap.wbmp";
	public static final String MIME_IMAGE_X_CMU_RASTER = "image/x-cmu-raster";
	public static final String MIME_IMAGE_X_ICON = "image/x-icon";
	public static final String MIME_IMAGE_X_PORTABLE_ANYMAP = "image/x-portable-anymap";
	public static final String MIME_IMAGE_X_PORTABLE_BITMAP = "image/x-portable-bitmap";
	public static final String MIME_IMAGE_X_PORTABLE_GRAYMAP = "image/x-portable-graymap";
	public static final String MIME_IMAGE_X_PORTABLE_PIXMAP = "image/x-portable-pixmap";
	public static final String MIME_IMAGE_X_RGB = "image/x-rgb";
	public static final String MIME_AUDIO_BASIC = "audio/basic";
	public static final String MIME_AUDIO_MIDI = "audio/midi";
	public static final String MIME_AUDIO_MPEG = "audio/mpeg";
	public static final String MIME_AUDIO_X_AIFF = "audio/x-aiff";
	public static final String MIME_AUDIO_X_MPEGURL = "audio/x-mpegurl";
	public static final String MIME_AUDIO_X_PN_REALAUDIO = "audio/x-pn-realaudio";
	public static final String MIME_AUDIO_X_WAV = "audio/x-wav";
	public static final String MIME_CHEMICAL_X_PDB = "chemical/x-pdb";
	public static final String MIME_CHEMICAL_X_XYZ = "chemical/x-xyz";
	public static final String MIME_MODEL_IGES = "model/iges";
	public static final String MIME_MODEL_MESH = "model/mesh";
	public static final String MIME_MODEL_VRLM = "model/vrml";
	public static final String MIME_TEXT_PLAIN = "text/plain";
	public static final String MIME_TEXT_RICHTEXT = "text/richtext";
	public static final String MIME_TEXT_RTF = "text/rtf";
	public static final String MIME_TEXT_HTML = "text/html";
	public static final String MIME_TEXT_CALENDAR = "text/calendar";
	public static final String MIME_TEXT_CSS = "text/css";
	public static final String MIME_TEXT_SGML = "text/sgml";
	public static final String MIME_TEXT_TAB_SEPARATED_VALUES = "text/tab-separated-values";
	public static final String MIME_TEXT_VND_WAP_XML = "text/vnd.wap.wml";
	public static final String MIME_TEXT_VND_WAP_WMLSCRIPT = "text/vnd.wap.wmlscript";
	public static final String MIME_TEXT_X_SETEXT = "text/x-setext";
	public static final String MIME_TEXT_X_COMPONENT = "text/x-component";
	public static final String MIME_VIDEO_QUICKTIME = "video/quicktime";
	public static final String MIME_VIDEO_MPEG = "video/mpeg";
	public static final String MIME_VIDEO_VND_MPEGURL = "video/vnd.mpegurl";
	public static final String MIME_VIDEO_X_MSVIDEO = "video/x-msvideo";
	public static final String MIME_VIDEO_X_MS_WMV = "video/x-ms-wmv";
	public static final String MIME_VIDEO_X_SGI_MOVIE = "video/x-sgi-movie";
	public static final String MIME_X_CONFERENCE_X_COOLTALK = "x-conference/x-cooltalk";

	private static final Map<String, String> mappings;

	static {
		val map = new HashMap<String, String>();
		map.put("xul", MIME_APPLICATION_VND_MOZZILLA_XUL_XML);
		map.put("json", MIME_APPLICATION_JSON);
		map.put("ice", MIME_X_CONFERENCE_X_COOLTALK);
		map.put("movie", MIME_VIDEO_X_SGI_MOVIE);
		map.put("avi", MIME_VIDEO_X_MSVIDEO);
		map.put("wmv", MIME_VIDEO_X_MS_WMV);
		map.put("m4u", MIME_VIDEO_VND_MPEGURL);
		map.put("mxu", MIME_VIDEO_VND_MPEGURL);
		map.put("htc", MIME_TEXT_X_COMPONENT);
		map.put("etx", MIME_TEXT_X_SETEXT);
		map.put("wmls", MIME_TEXT_VND_WAP_WMLSCRIPT);
		map.put("wml", MIME_TEXT_VND_WAP_XML);
		map.put("tsv", MIME_TEXT_TAB_SEPARATED_VALUES);
		map.put("sgm", MIME_TEXT_SGML);
		map.put("sgml", MIME_TEXT_SGML);
		map.put("css", MIME_TEXT_CSS);
		map.put("ifb", MIME_TEXT_CALENDAR);
		map.put("ics", MIME_TEXT_CALENDAR);
		map.put("wrl", MIME_MODEL_VRLM);
		map.put("vrlm", MIME_MODEL_VRLM);
		map.put("silo", MIME_MODEL_MESH);
		map.put("mesh", MIME_MODEL_MESH);
		map.put("msh", MIME_MODEL_MESH);
		map.put("iges", MIME_MODEL_IGES);
		map.put("igs", MIME_MODEL_IGES);
		map.put("rgb", MIME_IMAGE_X_RGB);
		map.put("ppm", MIME_IMAGE_X_PORTABLE_PIXMAP);
		map.put("pgm", MIME_IMAGE_X_PORTABLE_GRAYMAP);
		map.put("pbm", MIME_IMAGE_X_PORTABLE_BITMAP);
		map.put("pnm", MIME_IMAGE_X_PORTABLE_ANYMAP);
		map.put("ico", MIME_IMAGE_X_ICON);
		map.put("ras", MIME_IMAGE_X_CMU_RASTER);
		map.put("wbmp", MIME_IMAGE_WAP_WBMP);
		map.put("djv", MIME_IMAGE_VND_DJVU);
		map.put("djvu", MIME_IMAGE_VND_DJVU);
		map.put("svg", MIME_IMAGE_SVG_XML);
		map.put("ief", MIME_IMAGE_IEF);
		map.put("cgm", MIME_IMAGE_CGM);
		map.put("bmp", MIME_IMAGE_BMP);
		map.put("xyz", MIME_CHEMICAL_X_XYZ);
		map.put("pdb", MIME_CHEMICAL_X_PDB);
		map.put("ra", MIME_AUDIO_X_PN_REALAUDIO);
		map.put("ram", MIME_AUDIO_X_PN_REALAUDIO);
		map.put("m3u", MIME_AUDIO_X_MPEGURL);
		map.put("aifc", MIME_AUDIO_X_AIFF);
		map.put("aif", MIME_AUDIO_X_AIFF);
		map.put("aiff", MIME_AUDIO_X_AIFF);
		map.put("mp3", MIME_AUDIO_MPEG);
		map.put("mp2", MIME_AUDIO_MPEG);
		map.put("mp1", MIME_AUDIO_MPEG);
		map.put("mpga", MIME_AUDIO_MPEG);
		map.put("kar", MIME_AUDIO_MIDI);
		map.put("mid", MIME_AUDIO_MIDI);
		map.put("midi", MIME_AUDIO_MIDI);
		map.put("dtd", MIME_APPLICATION_XML_DTD);
		map.put("xsl", MIME_APPLICATION_XML);
		map.put("xml", MIME_APPLICATION_XML);
		map.put("xslt", MIME_APPLICATION_XSLT_XML);
		map.put("xht", MIME_APPLICATION_XHTML_XML);
		map.put("xhtml", MIME_APPLICATION_XHTML_XML);
		map.put("src", MIME_APPLICATION_X_WAIS_SOURCE);
		map.put("ustar", MIME_APPLICATION_X_USTAR);
		map.put("ms", MIME_APPLICATION_X_TROFF_MS);
		map.put("me", MIME_APPLICATION_X_TROFF_ME);
		map.put("man", MIME_APPLICATION_X_TROFF_MAN);
		map.put("roff", MIME_APPLICATION_X_TROFF);
		map.put("tr", MIME_APPLICATION_X_TROFF);
		map.put("t", MIME_APPLICATION_X_TROFF);
		map.put("texi", MIME_APPLICATION_X_TEXINFO);
		map.put("texinfo", MIME_APPLICATION_X_TEXINFO);
		map.put("tex", MIME_APPLICATION_X_TEX);
		map.put("tcl", MIME_APPLICATION_X_TCL);
		map.put("sv4crc", MIME_APPLICATION_X_SV4CRC);
		map.put("sv4cpio", MIME_APPLICATION_X_SV4CPIO);
		map.put("sit", MIME_APPLICATION_X_STUFFIT);
		map.put("swf", MIME_APPLICATION_X_SHOCKWAVE_FLASH);
		map.put("shar", MIME_APPLICATION_X_SHAR);
		map.put("sh", MIME_APPLICATION_X_SH);
		map.put("cdf", MIME_APPLICATION_X_NETCDF);
		map.put("nc", MIME_APPLICATION_X_NETCDF);
		map.put("latex", MIME_APPLICATION_X_LATEX);
		map.put("skm", MIME_APPLICATION_X_KOAN);
		map.put("skt", MIME_APPLICATION_X_KOAN);
		map.put("skd", MIME_APPLICATION_X_KOAN);
		map.put("skp", MIME_APPLICATION_X_KOAN);
		map.put("js", MIME_APPLICATION_X_JAVASCRIPT);
		map.put("hdf", MIME_APPLICATION_X_HDF);
		map.put("gtar", MIME_APPLICATION_X_GTAR);
		map.put("spl", MIME_APPLICATION_X_FUTURESPLASH);
		map.put("dvi", MIME_APPLICATION_X_DVI);
		map.put("dxr", MIME_APPLICATION_X_DIRECTOR);
		map.put("dir", MIME_APPLICATION_X_DIRECTOR);
		map.put("dcr", MIME_APPLICATION_X_DIRECTOR);
		map.put("csh", MIME_APPLICATION_X_CSH);
		map.put("cpio", MIME_APPLICATION_X_CPIO);
		map.put("pgn", MIME_APPLICATION_X_CHESS_PGN);
		map.put("vcd", MIME_APPLICATION_X_CDLINK);
		map.put("bcpio", MIME_APPLICATION_X_BCPIO);
		map.put("rm", MIME_APPLICATION_VND_RNREALMEDIA);
		map.put("ppt", MIME_APPLICATION_VND_MSPOWERPOINT);
		map.put("mif", MIME_APPLICATION_VND_MIF);
		map.put("grxml", MIME_APPLICATION_SRGS_XML);
		map.put("gram", MIME_APPLICATION_SRGS);
		map.put("smil", MIME_APPLICATION_RDF_SMIL);
		map.put("smi", MIME_APPLICATION_RDF_SMIL);
		map.put("rdf", MIME_APPLICATION_RDF_XML);
		map.put("ogg", MIME_APPLICATION_X_OGG);
		map.put("oda", MIME_APPLICATION_ODA);
		map.put("dmg", MIME_APPLICATION_OCTET_STREAM);
		map.put("lzh", MIME_APPLICATION_OCTET_STREAM);
		map.put("so", MIME_APPLICATION_OCTET_STREAM);
		map.put("lha", MIME_APPLICATION_OCTET_STREAM);
		map.put("dms", MIME_APPLICATION_OCTET_STREAM);
		map.put("bin", MIME_APPLICATION_OCTET_STREAM);
		map.put("mathml", MIME_APPLICATION_MATHML_XML);
		map.put("cpt", MIME_APPLICATION_MAC_COMPACTPRO);
		map.put("hqx", MIME_APPLICATION_MAC_BINHEX40);
		map.put("jnlp", MIME_APPLICATION_JNLP);
		map.put("ez", MIME_APPLICATION_ANDREW_INSET);
		map.put("txt", MIME_TEXT_PLAIN);
		map.put("ini", MIME_TEXT_PLAIN);
		map.put("c", MIME_TEXT_PLAIN);
		map.put("h", MIME_TEXT_PLAIN);
		map.put("cpp", MIME_TEXT_PLAIN);
		map.put("cxx", MIME_TEXT_PLAIN);
		map.put("cc", MIME_TEXT_PLAIN);
		map.put("chh", MIME_TEXT_PLAIN);
		map.put("java", MIME_TEXT_PLAIN);
		map.put("csv", MIME_TEXT_PLAIN);
		map.put("bat", MIME_TEXT_PLAIN);
		map.put("cmd", MIME_TEXT_PLAIN);
		map.put("asc", MIME_TEXT_PLAIN);
		map.put("rtf", MIME_TEXT_RTF);
		map.put("rtx", MIME_TEXT_RICHTEXT);
		map.put("html", MIME_TEXT_HTML);
		map.put("htm", MIME_TEXT_HTML);
		map.put("zip", MIME_APPLICATION_ZIP);
		map.put("rar", MIME_APPLICATION_X_RAR_COMPRESSED);
		map.put("gzip", MIME_APPLICATION_X_GZIP);
		map.put("gz", MIME_APPLICATION_X_GZIP);
		map.put("tgz", MIME_APPLICATION_TGZ);
		map.put("tar", MIME_APPLICATION_X_TAR);
		map.put("gif", MIME_IMAGE_GIF);
		map.put("jpeg", MIME_IMAGE_JPEG);
		map.put("jpg", MIME_IMAGE_JPEG);
		map.put("jpe", MIME_IMAGE_JPEG);
		map.put("tiff", MIME_IMAGE_TIFF);
		map.put("tif", MIME_IMAGE_TIFF);
		map.put("png", MIME_IMAGE_PNG);
		map.put("au", MIME_AUDIO_BASIC);
		map.put("snd", MIME_AUDIO_BASIC);
		map.put("wav", MIME_AUDIO_X_WAV);
		map.put("mov", MIME_VIDEO_QUICKTIME);
		map.put("qt", MIME_VIDEO_QUICKTIME);
		map.put("mpeg", MIME_VIDEO_MPEG);
		map.put("mpg", MIME_VIDEO_MPEG);
		map.put("mpe", MIME_VIDEO_MPEG);
		map.put("abs", MIME_VIDEO_MPEG);
		map.put("doc", MIME_APPLICATION_MSWORD);
		map.put("xls", MIME_APPLICATION_VND_MSEXCEL);
		map.put("eps", MIME_APPLICATION_POSTSCRIPT);
		map.put("ai", MIME_APPLICATION_POSTSCRIPT);
		map.put("ps", MIME_APPLICATION_POSTSCRIPT);
		map.put("pdf", MIME_APPLICATION_PDF);
		map.put("exe", MIME_APPLICATION_OCTET_STREAM);
		map.put("dll", MIME_APPLICATION_OCTET_STREAM);
		map.put("class", MIME_APPLICATION_OCTET_STREAM);
		map.put("jar", MIME_APPLICATION_JAVA_ARCHIVE);

		mappings = map;
	}

	public static void register(String ext, String mimeType) {
		mappings.put(ext, mimeType);
	}

	public static String getMimeType(String name) {
		String mimeType = lookupMimeType(name);
		if (mimeType == null) {
			mimeType = MIME_APPLICATION_OCTET_STREAM;
		}
		return mimeType;
	}
	
	public static String lookupMimeType(String name) {

		val position = name.lastIndexOf('.');
		val ext = name.substring(position + 1);

		return mappings.get(ext.toLowerCase());
	}
}
