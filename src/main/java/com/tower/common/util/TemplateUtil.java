package com.tower.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.rtf.RTFEditorKit;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

public class TemplateUtil {
	public static final String WORD_TEMPLATE = "/test.ftl";   
    public static final String TEMPLATE_PATH = "/doc";   
    public static final String PREVIEW_DOC = "/三方合同.doc";   
  
        public static Template configTemplate(HttpServletRequest request, String temp) throws IOException {   
        Configuration config = new Configuration();   
        ServletContext sc = request.getSession().getServletContext();   
        config.setDirectoryForTemplateLoading(new File(sc.getRealPath(TEMPLATE_PATH)));   
        config.setObjectWrapper(new DefaultObjectWrapper());   
        Template template = config.getTemplate(temp, "UTF-8");   
        return template;   
        }   
  
        public static void toPreview(HttpServletRequest request, String temp, Map<?, ?> root){   
            try {   
            String previewPath = request.getSession().getServletContext().getRealPath("")+PREVIEW_DOC;   
            Template template = configTemplate(request, temp);   
            FileOutputStream fos = new FileOutputStream(previewPath);   
            Writer out = new OutputStreamWriter(fos, "UTF-8");   
                template.process(root, out);   
                out.flush();   
                out.close();   
            } catch (Exception e) {   
            e.printStackTrace();   
            }   
        }  
        
        
        
        private  static Template configTemplate(String path, String temp) throws IOException {   
            Configuration config = new Configuration();   
            //ServletContext sc = request.getSession().getServletContext();   
            config.setDirectoryForTemplateLoading(new File(path));   
            config.setObjectWrapper(new DefaultObjectWrapper());   
            Template template = config.getTemplate(temp, "UTF-8");   
            return template;   
            }   
        public static boolean toPreview(String templatePath,String threePath, String temp, Map<?, ?> root){   
            boolean result=false;
        	try {   
            String previewPath = threePath;  
            File file=new File(previewPath);
            if(!file.getParentFile().exists())
            	file.mkdirs();
            Template template = configTemplate(templatePath, temp);   
            FileOutputStream fos = new FileOutputStream(previewPath);   
            Writer out = new OutputStreamWriter(fos, "UTF-8");   
                template.process(root, out);   
                out.flush();   
                out.close();  
                result=true;
            } catch (Exception e) {   
              e.printStackTrace();   
              
            }   
         return result;
        }  
        
        
        
        
      


}
