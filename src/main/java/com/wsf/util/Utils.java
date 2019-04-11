package com.wsf.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static void getQueryId(String[] paths){
        if(paths==null||paths.length==0) return;

        File[] filePaths=new File[paths.length];
        Pattern pattern=Pattern.compile("queryId:\"[0-9a-zA-Z]{32}\"");
        Matcher matcher=null;
        for(int i=0;i<filePaths.length;i++){
            filePaths[i]=new File(paths[i]);
        }
        StringBuilder sb=new StringBuilder();
        for(File f:filePaths){
            if(!f.exists()) continue;
            try(FileInputStream fis=new FileInputStream(f);
                InputStreamReader isr=new InputStreamReader(fis,"utf-8");
                BufferedReader br=new BufferedReader(isr);){
                String s="";

                while ((s=br.readLine())!=null){
                    sb.append(s);
                }

                matcher=pattern.matcher(sb.toString());
                System.out.println(f.getAbsolutePath()+":");
                while(matcher.find()){
                    System.out.println(matcher.group());
                }
                System.out.println();


            }catch (IOException e) {
                e.printStackTrace();
            }
        }



    }

    public static String[] getJsFiles(String folderPath){
        List<String> paths=new ArrayList<>();
        doGetJsFiles(folderPath,paths);
        return paths.toArray(new String[paths.size()]);
    }
    private static String[] doGetJsFiles(String folderPath,List<String> paths){
        if(folderPath==null) return null;

        File folder=new File(folderPath);

        if (!folder.exists()) return  null;


        File[] files=folder.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {

                if(pathname.getName().endsWith(".js")) return true;

                if(pathname.isDirectory()) return true;

                return false;

            }
        });

        for(File f:files){


            if(f.isFile()) {
                paths.add(f.getAbsolutePath());
            }else{
                doGetJsFiles(f.getAbsolutePath(),paths);
            }
        }





        return  null;
    }
    public static void main(String[] args) {





        getQueryId(getJsFiles(""));
    }

}
