/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author dell
 */
public class Listener implements ServletContextListener {
    Date date = new Date();
    SimpleDateFormat ft = new SimpleDateFormat ("E MM/dd/yyyy hh:mm:ss a zzz");
    
    @Override
    public void contextInitialized(ServletContextEvent footerdate){
        footerdate.getServletContext().setAttribute("date", ft.format(date));
    }
    @Override
    public void contextDestroyed(ServletContextEvent date){
        
    }
}
