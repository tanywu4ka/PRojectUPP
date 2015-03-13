package core;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.SocketException;
import java.security.SecureRandom;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import com.mysql.jdbc.StringUtils;

import view.BtnEdit;
import view.Button;
import view.DelBtn;
import view.Label;
import view.ScrollPane;
import core.App;





import entity.RouteName;
import entity.RouteStop;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Utils {

	public static void handleException(String error) {
		System.out.println(error);
	}

	public static String fileToString(String path) {
		File file = new File(path);

		String text = null;
		try {
			text = new Scanner(new File(file.getPath())).useDelimiter("\\A")
					.next();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return text;
	}

	public static boolean isPicture(String path) {
		boolean valid = true;
		try {
			Image image = ImageIO.read(new File(path));

			if (image == null) {
				valid = false;
			}
		} catch (IOException ex) {
			valid = false;
		}

		return valid;
	}
	
	public static String randStr() {
		SecureRandom random = new SecureRandom();
		return new BigInteger(130, random).toString(32);
	}

	public static BufferedImage getAdaptedImage(String texture, int width, int height) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(texture));
		} catch (IOException e) {
			e.printStackTrace();
		}

		BufferedImage newImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);

		Graphics gr = newImage.createGraphics();
		gr.drawImage(image, 0, 0, width, height, null);
		gr.dispose();

		return newImage;
	}

	public static Font getDefaultFont() {
		return new Font("Calibri", Font.BOLD, 15);
	}

	public static void alert(String message) {
		JOptionPane.showMessageDialog(App.GET.getFrame(), message);
	}

	public static void log(String text) {
		System.out.println(text);
	}

	public static JPanel wrapWithBorderLayoutPanel(JPanel panel) {
		JPanel borderLayoutPanel = new JPanel();
		borderLayoutPanel.setLayout(new BorderLayout());

		JScrollPane tileScrollPane = new ScrollPane(panel,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		borderLayoutPanel.setBounds(250, 0, App.GET.getWidth() - 240,
				App.GET.getHeight() + 10);
		borderLayoutPanel.add(tileScrollPane, BorderLayout.CENTER);

		return borderLayoutPanel;
	}

	public static JPanel wrapWithBorderLayoutTinyPanel(JPanel panel) {
		JPanel borderLayoutPanel = new JPanel();
		borderLayoutPanel.setLayout(new BorderLayout());

		JScrollPane tileScrollPane = new ScrollPane(panel,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		borderLayoutPanel.setBounds(10, 150, 150, 90);
		borderLayoutPanel.add(tileScrollPane, BorderLayout.CENTER);

		return borderLayoutPanel;
	}

	public static JLabel getResultTitle(String text, int width) {
		JLabel title = new Label(text);
		title.setFont(App.GET.getFont(25));
		title.setForeground(new Color(255, 255, 255));

		int w = App.GET.getWidth() - 250;
		if (width > w) {
			w = width;
		}

		title.setBounds(0, 0, w, 60);
		// title.setBackground(new Color(0, 119, 0));
		title.setBackground(new Color(58, 58, 58));
		title.setOpaque(true);
		title.setBorder(new EmptyBorder(0, 30, 0, 10));
		return title;
	}

	public static JLabel getAddTitle(String text, int width) {
		JLabel title = new Label(text);
		title.setFont(App.GET.getFont(25));
		title.setForeground(new Color(255, 255, 255));

		int w = App.GET.getWidth() - 250;
		if (width > w) {
			w = width;
		}

		title.setBounds(0, 0, w, 60);
		title.setBackground(new Color(58, 58, 58));
		title.setOpaque(true);
		title.setBorder(new EmptyBorder(0, 30, 0, 10));
		return title;
	}

	public static JPanel getRow(ArrayList<String> cells, int top, String type) {
		JPanel row = new JPanel();
		row.setLayout(null);

		int x = 20;
		int y = 0;
		int height = 40;
		int diff = 0;

		for (int i = 0; i < cells.size(); ++i) {
			if (i == 0) {
				diff = 50;
			} else {
				diff = 100;
			}

			Label l = new Label(cells.get(i));
			l.setLayout(null);
			l.setFont(App.GET.getFont(12));
			l.setBounds(x, y, diff, 40);
			l.setForeground(new Color(34, 34, 34));
			l.setHorizontalAlignment(JTextField.LEFT);
			row.add(l);

			x += diff;
		}

//		if (App.GET.isAdmin() && type != null) {
//			DelBtn del = new DelBtn("Del", type, cells.get(0));
//			del.setBounds(x + diff, y, diff - 10, 40);
//			row.add(del);
//
//			BtnEdit edit = new BtnEdit("Edit", type, cells.get(0));
//			edit.setBounds(x, y, diff - 10, 40);
//			row.add(edit);
//
//		}
//
//		if (App.GET.isAdmin()) {
//			x += diff * 2;
//		}

		if (x > App.GET.getWidth() - 240) {
			row.setBounds(0, top, x, height);
		} else {
			row.setBounds(0, top, App.GET.getWidth() - 240, height);
		}

		row.setBackground(new Color(226, 226, 226));

		return row;
	}
	
	public static JPanel getRowSimple(ArrayList<String> cells, int top, String type) {
		JPanel row = new JPanel();
		row.setLayout(null);

		int x = 10;
		int y = 0;
		int height = 40;
		int diff = 0;

		for (int i = 0; i < cells.size(); ++i) {
			if (i == 0) {
				diff = 25;
			} else {
				diff = 100;
			}

			Label l = new Label(cells.get(i));
			l.setLayout(null);
			l.setFont(App.GET.getFont(12));
			l.setBounds(x, y, diff, 40);
			l.setForeground(new Color(34, 34, 34));
			l.setHorizontalAlignment(JTextField.LEFT);
			row.add(l);

			x += diff;
		}


		if (x > App.GET.getWidth() - 240) {
			row.setBounds(0, top, x, height);
		} else {
			row.setBounds(0, top, App.GET.getWidth() - 240, height);
		}

		row.setBackground(new Color(226, 226, 226));

		return row;
	}

	public static ArrayList<String> toArrayRouteStop(RouteStop stop) {
		ArrayList<String> l = new ArrayList<String>();
		
		int pos = 1;
		
		l.add("" + (stop.getOrder_id() + 1));
		//l.add(DBApi.GET.getRouteNameById(stop.getRoute_id()));
		l.add(""+DBApi.GET.getCityById(stop.getCity_id()).getCity_name());
		
		//isStop now shown
		
		
		return l;
	}
	
//	public static ArrayList<String> toArrayAsteroidObject(Asteroid a) {
//		ArrayList<String> l = new ArrayList<String>();
//
//		l.add("" + a.getId());
//		l.add(a.getE_name());
//		l.add("" + a.getE_orbit());
//		l.add("" + a.getE_diameter());
//
//		// date
//		long currentTime = System.currentTimeMillis();
//		Date date = null;
//
//		Timestamp dt = a.getE_date();
//
//		if (dt == null) {
//			date = new Date(currentTime);
//		} else {
//			date = new Date(a.getE_date().getTime());
//		}
//
//		String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(date);
//		l.add(dateStr);
//
//		if (a.getE_whois_type() != null && !a.getE_whois_type().isEmpty()) {
//			String nm = DataBaseAPI.GET.getWhoisName(a.getE_whois_type(),
//					a.getE_whois());
//			l.add(a.getE_whois_type());
//			if (nm != null) {
//				l.add(nm);
//			} else {
//				l.add("none");
//			}
//
//		} else {
//			l.add("not a sattelite");
//			l.add("none");
//		}
//
//		if (a.getE_galaxy() != 0) {
//			l.add(DataBaseAPI.GET.getGalaxyByID(a.getE_galaxy()));
//		} else {
//			l.add("not in galaxy");
//		}
//
//		return l;
//	}

//	public static ArrayList<String> toArrayPlanetObject(Planet a) {
//		ArrayList<String> l = new ArrayList<String>();
//
//		l.add("" + a.getId());
//		l.add("" + a.getE_name());
//		l.add("" + a.getE_mass());
//		l.add("" + a.getE_orbit());
//		l.add("" + a.getE_diameter());
//		l.add("" + a.getE_period());
//		l.add("" + a.getE_distance());
//
//		if (a.isE_is_orphan()) {
//			l.add("Yes");
//		} else {
//			l.add("No");
//		}
//
//		// date
//		long currentTime = System.currentTimeMillis();
//		Date date = null;
//
//		Timestamp dt = a.getE_date();
//
//		if (dt == null) {
//			date = new Date(currentTime);
//		} else {
//			date = new Date(a.getE_date().getTime());
//		}
//		String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(date);
//		l.add(dateStr);
//
//		if (a.getE_whois_type() != null && !a.getE_whois_type().isEmpty()) {
//			String nm = DataBaseAPI.GET.getWhoisName(a.getE_whois_type(),
//					a.getE_whois());
//			l.add(a.getE_whois_type());
//			if (nm != null) {
//				l.add(nm);
//			} else {
//				l.add("none");
//			}
//
//		} else {
//			l.add("not a sattelite");
//			l.add("none");
//		}
//
//		if (a.getE_galaxy() != 0) {
//			l.add(DataBaseAPI.GET.getGalaxyByID(a.getE_galaxy()));
//		} else {
//			l.add("not in galaxy");
//		}
//
//		return l;
//	}

//	public static ArrayList<String> toArrayGalaxyObject(Galaxy a) {
//		ArrayList<String> l = new ArrayList<String>();
//
//		l.add("" + a.getId());
//		l.add("" + a.getE_name());
//		l.add("" + a.getE_mass());
//		l.add("" + a.getE_age());
//
//		l.add("" + a.getE_distance());
//
//		// date
//		long currentTime = System.currentTimeMillis();
//		Date date = null;
//
//		Timestamp dt = a.getE_date();
//
//		if (dt == null) {
//			date = new Date(currentTime);
//		} else {
//			date = new Date(a.getE_date().getTime());
//		}
//		String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(date);
//		l.add(dateStr);
//
//		return l;
//	}

//	public static ArrayList<String> toArrayStarObject(Star a) {
//		ArrayList<String> l = new ArrayList<String>();
//
//		l.add("" + a.getId());
//		l.add("" + a.getE_name());
//		l.add("" + a.getE_mass());
//		l.add("" + a.getE_radius());
//		l.add("" + a.getE_temperature());
//		l.add("" + a.getE_speed());
//		if (a.isE_is_orphan()) {
//			l.add("Yes");
//		} else {
//			l.add("No");
//		}
//		l.add("" + a.getE_distance());
//
//		// date
//		long currentTime = System.currentTimeMillis();
//		Date date = null;
//
//		Timestamp dt = a.getE_date();
//
//		if (dt == null) {
//			date = new Date(currentTime);
//		} else {
//			date = new Date(a.getE_date().getTime());
//		}
//		String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(date);
//		l.add(dateStr);
//
//		if (a.getE_galaxy() != 0) {
//			l.add(DataBaseAPI.GET.getGalaxyByID(a.getE_galaxy()));
//		} else {
//			l.add("not in galaxy");
//		}
//
//		return l;
//	}

//	public static ArrayList<String> toArrayHoleObject(BlackHole a) {
//		ArrayList<String> l = new ArrayList<String>();
//
//		l.add("" + a.getId());
//		l.add("" + a.getE_name());
//		l.add("" + a.getE_mass());
//		l.add("" + a.getE_radius());
//		l.add("" + a.getE_speed());
//
//		// date
//		long currentTime = System.currentTimeMillis();
//		Date date = null;
//
//		Timestamp dt = a.getE_date();
//
//		if (dt == null) {
//			date = new Date(currentTime);
//		} else {
//			date = new Date(a.getE_date().getTime());
//		}
//		String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(date);
//		l.add(dateStr);
//
//		if (a.getE_galaxy() != 0) {
//			l.add(DataBaseAPI.GET.getGalaxyByID(a.getE_galaxy()));
//		} else {
//			l.add("not in galaxy");
//		}
//
//		return l;
//	}

//	public static ArrayList<String> toArrayCometObject(Comet a) {
//		ArrayList<String> l = new ArrayList<String>();
//
//		l.add("" + a.getId());
//		l.add("" + a.getE_name());
//		l.add("" + a.getE_kernel());
//		l.add("" + a.getE_tale());
//
//		// date
//		long currentTime = System.currentTimeMillis();
//		Date date = null;
//
//		Timestamp dt = a.getE_date();
//
//		if (dt == null) {
//			date = new Date(currentTime);
//		} else {
//			date = new Date(a.getE_date().getTime());
//		}
//		String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(date);
//		l.add(dateStr);
//
//		if (a.getE_galaxy() != 0) {
//			l.add(DataBaseAPI.GET.getGalaxyByID(a.getE_galaxy()));
//		} else {
//			l.add("not in galaxy");
//		}
//
//		return l;
//	}

	public static JComboBox getDropDown(String[] strs) {
		JComboBox list = new JComboBox(strs);
		list.setFont(App.GET.getFont(18));
		list.setBackground(new Color(255, 255, 255));

		return list;
	}
	
	public static JComboBox getComboHours() {
		String [] nums = new String[24];
		for(int i = 0; i < 24; ++i) {
			nums[i] = String.valueOf(i);
			if (nums[i].length() < 2) {
				nums[i] = "0" + nums[i];
			}
		}
		
		return getDropDown(nums);
	}
	
	public static JComboBox getComboMinutes() {
		String [] nums = new String[12];
		for(int i = 0, j = 0; i < 12; ++i, j += 5) {
			nums[i] = String.valueOf(j);
			if (nums[i].length() < 2) {
				nums[i] = "0" + nums[i];
			}
		}
		
		return getDropDown(nums);
	}
	
	public static JComboBox getComboRoutes() {
		List<RouteName> routeNames = DBApi.GET.getRouteNames();
		String [] routes = new String[routeNames.size()];
		
		int index = 0;
		for(RouteName name: routeNames) {
			routes[index++] = name.getRoute_name();
		}
		
		return getDropDown(routes);
	}
	
	public static RouteName getRouteNameByName(String name) {
		List<RouteName> routeNames = DBApi.GET.getRouteNames();
		
		for (RouteName route: routeNames) {
			if (route.getRoute_name().equals(name)) {
				return route;
			}
		}
		
		return null;
	}

	public static JComboBox getWhoisList() {
		String[] types = { "None", "Planet", "Star" };
		return getDropDown(types);
	}

//	public static JComboBox getGalaxyList() {
//		List<String> pp = DataBaseAPI.GET.getGalaxiesArr();
//		pp.add(0, "None");
//		String[] gg = pp.toArray(new String[pp.size()]);
//
//		return getDropDown(gg);
//	}

	public static String parseIntField(String t) {
		if (t == null || t.isEmpty()) {
			t = "0";
		}

		return t;
	}

//	public static void uploadAndSms(InputStream fis) {
//		FTPClient ftpClient = new FTPClient();
//		//FileInputStream inputStream = null;
//
//		try {
//			ftpClient.connect("area730.com");
//			boolean login = ftpClient.login("webmaster", "mndfra19");
//
//			if (login) {
//				System.out.println("Connection established...");
//				//inputStream = new FileInputStream("fileToUpload.txt");
//				ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
//				String fileName = randStr();
//				boolean uploaded = ftpClient.storeFile("ftp/" + fileName + ".png",
//						fis);
//
//				if (uploaded) {
//					System.out.println("File uploaded successfully !");
//					String longUrl = "http://area730.com/ftp/" + fileName + ".png";
//					String shortUrl = shortenUrl(longUrl);
//					copyToClipboard(shortUrl);
//					String phone = App.GET.getUser().getUser_phone();
//					if (phone != null && !phone.equals("0")) {
//						sendSms(phone, "Link to the report is: " + shortUrl);
//					} else {
//						Utils.alert("Cant send you an SMS. Your phone is incorrect");
//					}
//
//					Utils.alert("Link is copied to clipboard.");
//				} else {
//					Utils.alert("Error in uploading file !");
//				}
//
//				// logout the user, returned true if logout successfully
//				boolean logout = ftpClient.logout();
//				if (logout) {
//					System.out.println("Connection close...");
//				}
//			} else {
//				System.out.println("Connection fail...");
//			}
//
//		} catch (SocketException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				ftpClient.disconnect();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}

//	public static String shortenUrl(String longUrl) {
//		longUrl = EncodingUtil.encodeURIComponent(longUrl);
//		String result = "";
//		
//		try  {
//			String urlParameters = "format=simple&url=" + longUrl;
//
//			String request = "http://is.gd/create.php";
//			URL url = new URL(request); 
//			HttpURLConnection connection = (HttpURLConnection) url.openConnection();           
//			connection.setDoOutput(true);
//			connection.setDoInput(true);
//			connection.setInstanceFollowRedirects(false); 
//			connection.setRequestMethod("POST"); 
//			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
//			connection.setRequestProperty("charset", "utf-8");
//			//connection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
//			connection.setUseCaches (false);
//
//			DataOutputStream wr = new DataOutputStream(connection.getOutputStream ());
//			wr.writeBytes(urlParameters);
//			wr.flush();
//			wr.close();
//			connection.disconnect();
//			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//			String inputLine;
//			StringBuffer response = new StringBuffer();
//			
//			while ((inputLine = in.readLine()) != null) {
//				response.append(inputLine);
//			}
//			result = response.toString();
//			System.out.println("Shrtened url response: " + result);
//			
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//		
//		return result;
//	}
//	
//	
//	private static String urlFor(String phone, String m) {
//		String url = "http://bytehand.com:3800/send?id=6273&key=756E12CD3A600BF1&to="+phone+"&from=tokmate.com&text=" + EncodingUtil.encodeURIComponent(m);;
//		
//		return url;
//	}
	
	private static void copyToClipboard(String data) {
		StringSelection stringSelection = new StringSelection (data);
		Clipboard clpbrd = Toolkit.getDefaultToolkit ().getSystemClipboard ();
		clpbrd.setContents (stringSelection, null);
	}
	
//	private static void sendSms(String phone, String mes) {
//		URL obj;
//
//		try {
//			obj = new URL(urlFor(phone, mes));
//			HttpURLConnection con;
//			con = (HttpURLConnection) obj.openConnection();
//			con.setRequestMethod("GET");
//			BufferedReader in = new BufferedReader(new InputStreamReader(
//					con.getInputStream()));
//			String inputLine;
//
//			while ((inputLine = in.readLine()) != null)
//				System.out.println(inputLine);
//
//			in.close();
//		} catch (IOException e2) {
//			// TODO Auto-generated catch block
//			e2.printStackTrace();
//
//		}
//	}
}
