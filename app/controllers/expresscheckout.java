package controllers;


	/*==================================================================
	 PayPal Express Checkout Call
	 ===================================================================
	*/


import java.util.HashMap;
import java.io.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.*;
import javax.servlet.http.*;

    public class expresscheckout  extends HttpServlet {

        public void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

            // Use "request" to read incoming HTTP headers (e.g. cookies)
            // and HTML form data (e.g. data the user entered and submitted)

            // Use "response" to specify the HTTP response line and headers
            // (e.g. specifying the content type, setting cookies).

            ///PrintWriter out = response.getWriter();
            // Use "out" to send content to browser
            ///  out.println("Hello World");


            HttpSession session = request.getSession(true);

            /*
            '-------------------------------------------
            ' The paymentAmount is the total value of
            ' the shopping cart, that was set
            ' earlier in a session variable
            ' by the shopping cart page
            '-------------------------------------------
            */

            String paymentAmount = (String) session.getAttribute("Payment_Amount");


            /*
            '------------------------------------
            ' The returnURL is the location where buyers return to when a
            ' payment has been succesfully authorized.
            '
            ' This is set to the value entered on the Integration Assistant
            '------------------------------------
            */

            String returnURL = "http://localhost:9000/homepage";

            /*
            '------------------------------------
            ' The cancelURL is the location buyers are sent to when they hit the
            ' cancel button during authorization of payment during the PayPal flow
            '
            ' This is set to the value entered on the Integration Assistant
            '------------------------------------
            */
            String cancelURL = "http://localhost:9000/contactpage";

            /*
            '------------------------------------
            ' Calls the SetExpressCheckout API call
            '
            ' The CallShortcutExpressCheckout function is defined in the file PayPalFunctions.asp,
            ' it is included at the top of this file.
            '-------------------------------------------------
            */
            paypalfunctions ppf = new paypalfunctions();
            HashMap nvp = ppf.CallShortcutExpressCheckout (paymentAmount, returnURL, cancelURL);
            String strAck = nvp.get("ACK").toString();
            if(strAck !=null && strAck.equalsIgnoreCase("Success"))
            {
                session.setAttribute("token", nvp.get("TOKEN").toString());
                //' Redirect to paypal.com
                response.sendRedirect(response.encodeRedirectURL( nvp.get("TOKEN").toString() ));
            }
            else
            {
                // Display a user friendly Error on the page using any of the following error information returned by PayPal

                String ErrorCode = nvp.get("L_ERRORCODE0").toString();
                String ErrorShortMsg = nvp.get("L_SHORTMESSAGE0").toString();
                String ErrorLongMsg = nvp.get("L_LONGMESSAGE0").toString();
                String ErrorSeverityCode = nvp.get("L_SEVERITYCODE0").toString();
            }
   }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
        throws ServletException, IOException {
      doGet(request, response);
    }

  }