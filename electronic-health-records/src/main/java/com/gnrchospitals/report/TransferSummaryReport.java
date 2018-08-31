package com.gnrchospitals.report;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gnrchospitals.util.LoginDBConnection;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

@WebServlet(urlPatterns = { "/transfer.report" })
public class TransferSummaryReport extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String ipNumber = (String)session.getAttribute("IpNo");
		
		
			try {
				Connection con = LoginDBConnection.getConnection();
				JasperReport jasperReport = null;
				JasperDesign jasperDesign = null;
				Map<String, Object> parameters = new HashMap<>();

				String path = getServletContext().getRealPath("/WEB-INF/views/");
				System.out.println("PDF path is" + path);
				jasperDesign = JRXmlLoader.load(path + "/sample1.jrxml");
				JRDesignQuery query = new JRDesignQuery();
				query.setText("select d.EHR_DTL_CODE,  d.EHR_CRT_DT, a.RRH_FIRST_NAME||' '||a.RRH_MIDDLE_NAME||' '||a.RRH_LAST_NAME name, b.WAT_IP_NUM IP_NUM, a.RRH_MR_NUM, " + 
						"                        max(decode(d.ehr_attrb_code,'TS001' , d.EHR_ATTRB_VALUE, '')) TS001, " + 
						"                        max(decode(d.ehr_attrb_code,'TS002' , d.EHR_ATTRB_VALUE, '')) TS002, " + 
						"                        max(decode(d.ehr_attrb_code,'TS003' , d.EHR_ATTRB_VALUE, '')) TS003, " + 
						"                        max(decode(d.ehr_attrb_code,'TS004' , d.EHR_ATTRB_VALUE, '')) TS004, " + 
						"                        max(decode(d.ehr_attrb_code,'TS005' , d.EHR_ATTRB_VALUE, '')) TS005, " + 
						"                        max(decode(d.ehr_attrb_code,'TS006' , d.EHR_ATTRB_VALUE, '')) TS006, " + 
						"                        max(decode(d.ehr_attrb_code,'TS007' , d.EHR_ATTRB_VALUE, '')) TS007, " + 
						"                        max(decode(d.ehr_attrb_code,'TS008' , d.EHR_ATTRB_VALUE, '')) TS008, " + 
						"                        max(decode(d.ehr_attrb_code,'TS009' , d.EHR_ATTRB_VALUE, '')) TS009, " + 
						"                        max(decode(d.ehr_attrb_code,'TS010' , d.EHR_ATTRB_VALUE, '')) TS010, " + 
						"                        max(decode(d.ehr_attrb_code,'TS011' , d.EHR_ATTRB_VALUE, '')) TS011, " + 
						"                        max(decode(d.ehr_attrb_code,'TS012' , d.EHR_ATTRB_VALUE, '')) TS012, " + 
						"                        max(decode(d.ehr_attrb_code,'TS013' , d.EHR_ATTRB_VALUE, '')) TS013, " + 
						"                        max(decode(d.ehr_attrb_code,'TS014' , d.EHR_ATTRB_VALUE, '')) TS014, " + 
						"                        max(decode(d.ehr_attrb_code,'TS015' , d.EHR_ATTRB_VALUE, '')) TS015, " + 
						"                        max(decode(d.ehr_attrb_code,'TS016' , d.EHR_ATTRB_VALUE, '')) TS016, " + 
						"                         replace(max(decode( d.ehr_attrb_code,'TS017' , substr(d.EHR_ATTRB_VALUE,2, length(d.EHR_ATTRB_VALUE)-2), '')), '-', chr(13)) TS017 " + 
						"        from   RE_REGISTRATION_HEADER a, " + 
						"               WA_ADMISSION_TXN b , " + 
						"               EMR_CLINICAL_DETAIL c, " + 
						"               emr_health_record d " + 
						"              where b.WAT_MR_NUM = a.RRH_MR_NUM " + 
						"                    and WAT_pat_status = 'ADIP' " + 
						"                    and c.ECD_PAT_NUM = b.WAT_IP_NUM " + 
						"                    and c.ECD_PAT_NUM = '"+ ipNumber +"' " + 
						"                    and d.EHR_ATTRB_CODE in (select EAM_ATTRB_CODE from EMR_ATTRIBUTE_MASTER where EAM_ATTRB_TYPE = 'TSNS') " + 
						"                    and d.EHR_EMR_NUM = c.ECD_EM_NUM " + 
						"                    group by    d.EHR_DTL_CODE,  d.EHR_CRT_DT, a.RRH_FIRST_NAME||' '||a.RRH_MIDDLE_NAME||' '||a.RRH_LAST_NAME , b.WAT_IP_NUM , a.RRH_MR_NUM");
				
				System.out.println("Jasper Query is : " + query);
				
				jasperDesign.setQuery(query);
				jasperReport = JasperCompileManager.compileReport(jasperDesign);
				byte[] byteStream = JasperRunManager.runReportToPdf(jasperReport, parameters, con);
				OutputStream out = response.getOutputStream();
				response.setContentType("application/pdf");
				response.setContentLength(byteStream.length);
				out.write(byteStream, 0, byteStream.length);
			} catch (JRException e) {
				// TODO Auto-generated catch  block
				e.printStackTrace();
			}
			
			

		

	}

}
