package com.ibm.timetracker.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class MacAddressAndIpFinder {

	private static Logger LOG = Logger.getLogger("IlcTracker");
	private static final IPAddressValidator iPAddressValidator= new IPAddressValidator();

	public static void main(String[] args) throws SocketException {
		// System.out.println(getData());

		try {
			LOG.info("Full list of Network Interfaces:");
			StringBuffer sBuf = new StringBuffer();
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				// System.out.println("    " + intf.getName() + " " +
				// intf.getDisplayName());
				StringBuffer tempBuf = new StringBuffer();
				//Get Mac Address
				StringBuffer macBuf = new StringBuffer();
				byte[] mac = intf.getHardwareAddress();
				if(mac != null)
				{
					for (int i = 0; i < mac.length; i++) {
						macBuf.append(String.format("%02X%s", mac[i],
								(i < mac.length - 1) ? "-" : ""));
					}
				}
				
				String str = intf.getName() + " " + intf.getDisplayName()
						+ "  " + macBuf.toString() + "\n";
				// intf.getInetAddresses().getClass(
				int count = 0;
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					// System.out.println("        " +
					// enumIpAddr.nextElement().toString());
					tempBuf.append("        "
							+ enumIpAddr.nextElement().toString());
					count++;
				}
				if (tempBuf.length() > 0 && count > 1) {
					sBuf.append(str).append("\n").append(tempBuf).append("\n");
				}
			}
			System.out.println(sBuf);
		} catch (SocketException e) {
			LOG.info(" (error retrieving network interface list)");
		}

		System.out.println(getData());
		
		System.out.println("Ip: " + GetAddress("ip"));
		System.out.println("Mac: " + GetAddress("mac"));
		System.out.println("================================================================");
		System.out.println("Get all network data:"+ getAllNetworkData());
		System.out.println("Computer name:" + getComputerName());

	}
	
	public static String getAllNetworkData() throws SocketException
	{
		StringBuffer tempBuf = new StringBuffer();
		for (Enumeration<NetworkInterface> en = NetworkInterface
				.getNetworkInterfaces(); en.hasMoreElements();) {
			NetworkInterface intf = en.nextElement();
			// System.out.println("    " + intf.getName() + " " +
			// intf.getDisplayName());
			
			//Get Mac Address
			StringBuffer macBuf = new StringBuffer();
			byte[] mac = intf.getHardwareAddress();
			if(mac != null)
			{
				for (int i = 0; i < mac.length; i++) {
					macBuf.append(String.format("%02X%s", mac[i],
							(i < mac.length - 1) ? "-" : ""));
				}
			}else
			{
				continue;
			}
			
			//System.out.println("Mac Address:"+macBuf);
			boolean append = false;
			int count = 0;
			for (Enumeration<InetAddress> enumIpAddr = intf
					.getInetAddresses(); enumIpAddr.hasMoreElements();) {
				// System.out.println("        " +
				// enumIpAddr.nextElement().toString());
				InetAddress nextElement = enumIpAddr.nextElement();
				if(nextElement != null)
				{
					String ipAddress = nextElement.toString().replaceAll("^/+", "");
					if(iPAddressValidator.validate(ipAddress))
					{
						tempBuf.append(ipAddress).append(":");
						append = true;
						break;
					}
				}
			}
			if(append )
			{
				tempBuf.append(macBuf).append("#");
			}
			/*if (tempBuf.length() > 0 && count > 1) {
				sBuf.append(str).append("\n").append(tempBuf).append("\n");
			}*/
			
		}
		int lastCharPos = tempBuf.length()-1;
		if(tempBuf.charAt(lastCharPos) == '#')
		{
			tempBuf.deleteCharAt(lastCharPos);
		}
		return tempBuf.toString();
	}

	public static Map<String, String> getData() {
		Map<String, String> dataMap = new HashMap<>();
		InetAddress ip;
		try {

			ip = InetAddress.getLocalHost();
			// System.out.println("Current IP address : " +
			// ip.getHostAddress());
			dataMap.put("IP_ADDRESS", ip.getHostAddress());
			NetworkInterface network = NetworkInterface.getByInetAddress(ip);

			byte[] mac = network.getHardwareAddress();

			System.out.print("Current MAC address : ");

			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < mac.length; i++) {
				sb.append(String.format("%02X%s", mac[i],
						(i < mac.length - 1) ? "-" : ""));
			}
			dataMap.put("MAC_ADDRESS", sb.toString());
			// System.out.println(sb.toString());

		} catch (UnknownHostException e) {

			e.printStackTrace();

		} catch (SocketException e) {

			e.printStackTrace();

		}

		try {
			InetAddress localhost = InetAddress.getLocalHost();
			LOG.info(" IP Addr: " + localhost.getHostAddress());
			// Just in case this host has multiple IP addresses....
			InetAddress[] allMyIps = InetAddress.getAllByName(localhost
					.getCanonicalHostName());
			if (allMyIps != null && allMyIps.length > 1) {
				LOG.info(" Full list of IP addresses:");
				for (int i = 0; i < allMyIps.length; i++) {
					LOG.info("    " + allMyIps[i]);
				}
			}
		} catch (UnknownHostException e) {
			LOG.info(" (error retrieving server host name)");
		}

		/*
		 * try { LOG.info("Full list of Network Interfaces:"); for
		 * (Enumeration<NetworkInterface> en = NetworkInterface
		 * .getNetworkInterfaces(); en.hasMoreElements();) { NetworkInterface
		 * intf = en.nextElement(); LOG.info("    " + intf.getName() + " " +
		 * intf.getDisplayName()); for (Enumeration<InetAddress> enumIpAddr =
		 * intf .getInetAddresses(); enumIpAddr.hasMoreElements();) {
		 * LOG.info("        " + enumIpAddr.nextElement().toString()); } } }
		 * catch (SocketException e) {
		 * LOG.info(" (error retrieving network interface list)"); }
		 */
		return dataMap;
	}

	public static String GetAddress(String addressType) {
		String address = "";
		InetAddress lanIp = null;
		try {

			String ipAddress = null;
			Enumeration<NetworkInterface> net = null;
			net = NetworkInterface.getNetworkInterfaces();
			while (net.hasMoreElements()) {
				NetworkInterface element = net.nextElement();
				Enumeration<InetAddress> addresses = element.getInetAddresses();
				while (addresses.hasMoreElements()) {
					InetAddress ip = addresses.nextElement();
					if (ip instanceof Inet4Address) {

						if (ip.isSiteLocalAddress()) {

							ipAddress = ip.getHostAddress();
							lanIp = InetAddress.getByName(ipAddress);
						}

					}

				}
			}

			if (lanIp == null)
				return null;

			if (addressType.equals("ip")) {

				address = lanIp.toString().replaceAll("^/+", "");

			} else if (addressType.equals("mac")) {

				address = GetMacAddress(lanIp);

			} else {

				throw new Exception("Specify \"ip\" or \"mac\"");

			}

		} catch (UnknownHostException e) {

			e.printStackTrace();

		} catch (SocketException e) {

			e.printStackTrace();

		} catch (Exception e) {

			e.printStackTrace();

		}

		return address;

	}

	private static String GetMacAddress(InetAddress ip) {
		String address = null;
		try {

			NetworkInterface network = NetworkInterface.getByInetAddress(ip);
			byte[] mac = network.getHardwareAddress();

			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < mac.length; i++) {
				sb.append(String.format("%02X%s", mac[i],
						(i < mac.length - 1) ? "-" : ""));
			}
			address = sb.toString();

		} catch (SocketException e) {

			e.printStackTrace();

		}

		return address;
	}
	
	public static String getComputerName()
	{
		String hostname = null;
		try
		{
		    InetAddress addr;
		    addr = InetAddress.getLocalHost();
		    hostname = addr.getHostName();
		}
		catch (UnknownHostException ex)
		{
		    System.out.println("Hostname can not be resolved");
		    
		}
		return hostname;
	}

}
