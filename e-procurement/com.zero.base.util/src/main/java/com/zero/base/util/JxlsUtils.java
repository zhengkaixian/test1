package com.zero.base.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.jxls.common.Context;
import org.jxls.expression.JexlExpressionEvaluator;
import org.jxls.transform.Transformer;
import org.jxls.util.JxlsHelper;

/**
 * 
 * @author
 *
 */
public class JxlsUtils{
	
	private static final String TEMPLATE_PATH="jxls-template";
	
	private Map<String,Integer> countMap = new HashMap<>(); //用于jxls的foreach循环计数
	
	public static void exportExcel(InputStream is, OutputStream os, Map<String, Object> model) throws IOException{
        Context context = new Context();
        if (model != null) {
            for (String key : model.keySet()) {
                context.putVar(key, model.get(key));
            }
        }
        JxlsHelper jxlsHelper = JxlsHelper.getInstance();
        Transformer transformer  = jxlsHelper.createTransformer(is, os);
        JexlExpressionEvaluator evaluator = (JexlExpressionEvaluator)transformer.getTransformationConfig().getExpressionEvaluator();
        Map<String, Object> funcs = new HashMap<String, Object>();
        funcs.put("utils", new JxlsUtils());    //添加自定义功能
        evaluator.getJexlEngine().setFunctions(funcs);
        jxlsHelper.processTemplate(context, transformer);
	}
	
	/**
	 * excel模版的批注的内容：jx:each(items="riskLists" var="riskList" lastCell="I2")
	 * 自定义功能，如计数（导出excel时下标就可以用${utils:count("riskList")}来算）
	 * @param var
	 * @return
	 */
	public Integer count(String var){
		if (var == null) return null;
		
		if(countMap.containsKey(var)){
		    Integer t = countMap.get(var);
		    t += 1;
		    countMap.replace(var, t);
		    return t;
		}else{
			countMap.put(var,1);
		}
		return 1;
	}

    public static void exportExcel(File xls, File out, Map<String, Object> model) throws FileNotFoundException, IOException {
            exportExcel(new FileInputStream(xls), new FileOutputStream(out), model);
    }
    
    public static void exportExcel(String templateName, OutputStream os, Map<String, Object> model) throws FileNotFoundException, IOException {
    	File template = getTemplate(templateName);
    	if(template!=null){
        	exportExcel(new FileInputStream(template), os, model);	
    	}
    }
    
    
    //获取jxls模版文件

    public static File getTemplate(String name){
        String templatePath = JxlsUtils.class.getClassLoader().getResource(TEMPLATE_PATH).getPath();
       // InputStream in = this.getClass().getClassLoader().getResourceAsStream("excel/test.xls");   //得到文档的路径
        File template = new File(templatePath, name);
        if(template.exists()){
            return template;
        }
        return null;
    }	
	
    // 日期格式化
    public String dateFmt(Date date, String fmt) {
        if (date == null) {
            return "";
        }
        try {
            SimpleDateFormat dateFmt = new SimpleDateFormat(fmt);
            return dateFmt.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    
    // if判断
    public Object ifelse(boolean b, Object o1, Object o2) {
        return b ? o1 : o2;
    }
    
}
