import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MainFrame extends JFrame implements ActionListener{
	private DateSpinner startSpinner;
	private DateSpinner endSpinner;
	private String strStartDay;
	private String strStartMonth;
	private String strStartYear;
	private String strStartDate;
	private String strEndDay;
	private String strEndMonth;
	private String strEndYear;
	private String strEndDate;
	private Date startDate;
	private Date endDate;
	private JButton okButton;
	private JComboBox<String> tickerSymbolesList;
	private String company;
	private String[] shortMonth = {"Jan","Feb","Mar","Apr","May","Jun","Jul",
			"Aug","Sep","Oct","Nov","Dec"};
	public MainFrame() {
		// TODO Auto-generated constructor stub
		setTitle("Ticker sysmbol");
		setSize(500,200);
		JPanel panel = new JPanel();
		JPanel tickerPanel = new JPanel();
		tickerPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("ticker name"), 
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		
		Container contentPane = this.getContentPane(); 
		JLabel tickerLabel = new JLabel("Ticker Name:");
		tickerLabel.setHorizontalTextPosition(JLabel.CENTER);
		tickerPanel.add(tickerLabel);
		//ticker name
		String []tickerSymboles = {
				"AAPL",
				"GOOG",
				"YHOO"
		};
		//default value
		company = "AAPL";
		tickerSymbolesList = new JComboBox(tickerSymboles);
//		tickerSymbolesList.setPreferredSize(preferredSize);
		tickerSymbolesList.setEditable(true);
		tickerPanel.add(tickerSymbolesList);
		tickerSymbolesList.addActionListener(this);
		contentPane.add(tickerPanel,BorderLayout.NORTH);
		
		JPanel datePanel = new JPanel();
		datePanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("Date: "), 
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		//start spinner
		JLabel startLabel = new JLabel("Start date: ");
		startSpinner = new DateSpinner("Start date: ","start");
		JLabel endLabel = new JLabel("End date: ");
		endSpinner = new DateSpinner("End date: ","end");

		datePanel.add(startLabel);
		datePanel.add(startSpinner.returnDateSpinner());
		datePanel.add(endLabel);
		datePanel.add(endSpinner.returnDateSpinner());
		contentPane.add(datePanel,BorderLayout.CENTER);
		
		//okButton
		startSpinner.setStringTemp("start");
		okButton = new JButton("OK");
		okButton.addActionListener(this);
		panel.add(okButton);
		contentPane.add(panel,BorderLayout.SOUTH);
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object object = e.getSource();

		if (object == okButton) {
			getStartDate();
			getEndtDate();

			Calendar cal=Calendar.getInstance();
			Date currentDate = cal.getTime();
			System.out.println(startDate);
			System.out.println(endDate);
			
//			if (startDate.getTime() > currentDate.getTime()) {
			if (startDate.after(currentDate)) {
				startDate = currentDate;
				int year = cal.get(Calendar.YEAR);//获取年份
			    int month=cal.get(Calendar.MONTH);//获取月份
			    int day=cal.get(Calendar.DATE);//获取日
		    	strStartDay = Integer.toString(day);
		    	strStartMonth = shortMonth[month];
		    	strStartYear = Integer.toString(year);
				startSpinner.setDate(currentDate);
			}
			if (endDate.after(currentDate)) {
				endDate = currentDate;
				int year = cal.get(Calendar.YEAR);//获取年份
			    int month=cal.get(Calendar.MONTH);//获取月份
			    int day=cal.get(Calendar.DATE);//获取日
		    	strEndDay = Integer.toString(day);
		    	strEndMonth = shortMonth[month];
		    	strEndYear = Integer.toString(year);
				endSpinner.setDate(currentDate);
			}
			
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
			String str1 = df.format(startDate);
			String str2 = df.format(endDate);
			if (startDate.after(endDate)) {
				JOptionPane.showMessageDialog(this.getContentPane(),
						"start day is before end date, please input it again!", "系统信息", JOptionPane.INFORMATION_MESSAGE);
			}			
			else if (str1.equals(str2)) {
				JOptionPane.showMessageDialog(this.getContentPane(),
						"start day is the same day with end date, please input it again!", "系统信息", JOptionPane.INFORMATION_MESSAGE);
			}
			else if (startDate.before(endDate)) {
				try {
					strStartDate = strStartMonth + "+" + strStartDay + "+" + strStartYear;
					strEndDate = strEndMonth + "+" + strEndDay + "+" + strEndYear;
					GetDataByURL getDataByURL = new GetDataByURL(company, strStartDate, strEndDate);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		}
		else if (object == tickerSymbolesList) {
			JComboBox cb = (JComboBox)e.getSource();
			String str = (String)cb.getSelectedItem();
			company = str;
			System.out.println(company);
		}
	}
	
	public void getStartDate(){		
		strStartDay = startSpinner.getDay();
		strStartMonth = startSpinner.getMonth();
		strStartYear = startSpinner.getYear();
		startDate = startSpinner.getDate();
		System.out.println("This is " + startDate);
//		strStartDate = strStartMonth + "+" + strStartDay + "+" + strStartYear;
        System.out.println("!!!Start day: "  + strStartDate);
	}
	
	public void getEndtDate(){
		
		strEndDay = endSpinner.getDay();
		strEndMonth = endSpinner.getMonth();
		strEndYear = endSpinner.getYear();
//		strEndDate = strEndMonth + "+" + strEndDay + "+" + strEndYear;
		endDate = endSpinner.getDate();
		System.out.println("This is " + endDate);
        System.out.println("!!!End day: " + strEndDate);
	}


}
