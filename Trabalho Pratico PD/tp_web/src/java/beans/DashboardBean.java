/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.year;
import controllers.AquisicaoPropostaFacadeLocal;
import controllers.PropostaFacadeLocal;
import controllers.UtilizadorFacadeLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped; 
import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.ItemSelectEvent;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
 
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author hddlucas
 */
@Named(value = "dashboardBean")
@SessionScoped
public class DashboardBean implements Serializable {

    @EJB
    private PropostaFacadeLocal propostaFacade;

    @EJB
    private AquisicaoPropostaFacadeLocal aquisicaoPropostaFacade;

    @EJB
    private UtilizadorFacadeLocal utilizadorFacade;

    
    
    /**
     * Creates a new instance of DashboardBean
     */
    
    
    public DashboardBean() {
    }
    
    private PieChartModel pieModel1;
    private LineChartModel dateModel;
    
    @PostConstruct
    public void init()  {
        createPieModels();
        createDateModel();
       
    }
     
    public PieChartModel getPieModel1() {
        return pieModel1;
    }
  
    private void createPieModels() {
        createPieModel1();
    }
     
    private void createPieModel1() {
        pieModel1 = new PieChartModel();
         
        pieModel1.set("Administradores", utilizadorFacade.getTotalUsersPerRole("admin"));
        pieModel1.set("Vendedores", utilizadorFacade.getTotalUsersPerRole("vendedor"));
        pieModel1.set("Avaliadores", utilizadorFacade.getTotalUsersPerRole("avaliador"));
        pieModel1.set("Agentes", utilizadorFacade.getTotalUsersPerRole("agente"));
         
        pieModel1.setTitle("Utilizadores do Sistema");
        pieModel1.setLegendPosition("w");
    }
 
    public void itemSelect(ItemSelectEvent event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item selected",
                        "Item Index: " + event.getItemIndex() + ", Series Index:" + event.getSeriesIndex());
         
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    
     public LineChartModel getDateModel() {
        return dateModel;
    }
     
    private void createDateModel()  {
        FacesContext context = FacesContext.getCurrentInstance();

        dateModel = new LineChartModel();
        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel("Series 1");
 
        Calendar calendar = Calendar.getInstance();
        int year = 2018;
        int month=2;
        
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month-1); // since 0 = January
        int maxDaysInMonth = calendar.getActualMaximum(Calendar.DATE);
        String dateAsString="";   
        Date date ; 
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        
        for (int day = 1; day <= maxDaysInMonth; day++) {
            // format date in such a way, that month and days are padded with zeroes up to 2 digits
            dateAsString = String.format("%d-%02d-%02d", year, month, day);
            try {
                date=formatter.parse(dateAsString);
                series1.set(dateAsString, aquisicaoPropostaFacade.totalPropostas(date));
                context.addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", date.toString()));
            } catch (Exception ex) {
                series1.set(dateAsString, 0);
            }

         
        }
 
        dateModel.addSeries(series1);
         
        dateModel.setTitle("Total de Propostas de Aquisição - Mês Corrente");
        dateModel.setZoom(true);
        dateModel.getAxis(AxisType.Y).setLabel("Total");
        DateAxis axis = new DateAxis("Datas");
        axis.setTickAngle(-50);
        axis.setMax(dateAsString);
        axis.setTickFormat("%b %#d, %y");
         
        dateModel.getAxes().put(AxisType.X, axis);
    }
    
     public int getTotalPropostas()  {
        return aquisicaoPropostaFacade.totalPropostas();
    }
     
    public int getTotalPropostasGanhas()  {
        return propostaFacade.getTotalWin();
    }
   
    public double getTotalValorTransacionado()  {
        return propostaFacade.getTotalTransactedMoney();
    }
    
}
