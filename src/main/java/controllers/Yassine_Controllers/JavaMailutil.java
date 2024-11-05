package controllers.Yassine_Controllers;

import javax.mail.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavaMailutil {

    public static  void SendMail(String recepient,String Pass) throws Exception{
        System.out.println("Preparing to Send email ");
        Properties properties = new Properties();

        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","smtp.googlemail.com");
        properties.put("mail.smtp.port","587");

        String myAccountEmail = "artyvenci@gmail.com";
        String pasword = "eziw vkjm nkks ment";


        Session session = Session.getInstance(properties, new Authenticator()
        {
            @Override
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(myAccountEmail,pasword);
            }

        });

        Message message = prepareMessage(session,myAccountEmail,recepient,Pass);
        Transport.send(message);
        System.out.println("Message sends succefully");
    }
    private  static Message prepareMessage(Session session , String myAccountEmail , String recepient , String Pass){
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO,new InternetAddress(recepient));
            message.setSubject("Récupération de compte ArtyVenci");
            String htmlCode ="<head>\n" +
                    "    <meta content=\"text/html; charset=utf-8\" http-equiv=\"Content-Type\" />\n" +

                    "    <meta name=\"description\" content=\"Reset Password Email Template.\">\n" +
                    "    <style type=\"text/css\">\n" +
                    "        a:hover {text-decoration: underline !important;}\n" +
                    "    </style>\n" +
                    "</head>\n" +
                    "\n" +
                    "<body marginheight=\"0\" topmargin=\"0\" marginwidth=\"0\" style=\"margin: 0px; background-color: #f2f3f8;\" leftmargin=\"0\">\n" +
                    "    <!--100% body table-->\n" +
                    "    <table cellspacing=\"0\" border=\"0\" cellpadding=\"0\" width=\"100%\" bgcolor=\"#f2f3f8\"\n" +
                    "        style=\"@import url(https://fonts.googleapis.com/css?family=Rubik:300,400,500,700|Open+Sans:300,400,600,700); font-family: 'Open Sans', sans-serif;\">\n" +
                    "        <tr>\n" +
                    "            <td>\n" +
                    "                <table style=\"background-color: #f2f3f8; max-width:670px;  margin:0 auto;\" width=\"100%\" border=\"0\"\n" +
                    "                    align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                    "                    <tr>\n" +
                    "                        <td style=\"height:80px;\">&nbsp;</td>\n" +
                    "                    </tr>\n" +
                    "                    <tr>\n" +
                    "                        <td style=\"text-align:center;\">\n" +
                    "                          <a href=\"https://rakeshmandal.com\" title=\"logo\" target=\"_blank\">\n" +
                    "                            <img width=\"150\" src=\"https://i.ibb.co/q1tznwp/422753690-204703712734911-8704086809119957789-n.png\" title=\"logo\" alt=\"logo\">\n" +
                    "                          </a>\n" +
                    "                        </td>\n" +
                    "                    </tr>\n" +
                    "                    <tr>\n" +
                    "                        <td style=\"height:20px;\">&nbsp;</td>\n" +
                    "                    </tr>\n" +
                    "                    <tr>\n" +
                    "                        <td>\n" +
                    "                            <table width=\"95%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\"\n" +
                    "                                style=\"max-width:670px;background:#fff; border-radius:3px; text-align:center;-webkit-box-shadow:0 6px 18px 0 rgba(0,0,0,.06);-moz-box-shadow:0 6px 18px 0 rgba(0,0,0,.06);box-shadow:0 6px 18px 0 rgba(0,0,0,.06);\">\n" +
                    "                                <tr>\n" +
                    "                                    <td style=\"height:40px;\">&nbsp;</td>\n" +
                    "                                </tr>\n" +
                    "                                <tr>\n" +
                    "                                    <td style=\"padding:0 35px;\">\n" +
                    "                                        <h1 style=\"color:#1e1e2d; font-weight:500; margin:0;font-size:32px;font-family:'Rubik',sans-serif;\">You have\n" +
                    "                                           Vous avez demandé de récupérer votre compte</h1>\n" +
                    "                                        <span\n" +
                    "                                            style=\"display:inline-block; vertical-align:middle; margin:29px 0 26px; border-bottom:1px solid #cecece; width:100px;\"></span>\n" +
                    "                                        <p style=\"color:#455056; font-size:15px;line-height:24px; margin:0;\">\n" +
                    "                                            Voici les informations de récupération de votre compte :\n\n" +
                    "                                            Mot de passe : " + Pass + "\n\n" +
                    "Si vous n'avez pas demandé cette récupération ou si vous avez des préoccupations, veuillez contacter immédiatement notre équipe d'assistance.\n\n" +
                    "                                        </p>\n" +

                    "                                    </td>\n" +
                    "                                </tr>\n" +
                    "                                <tr>\n" +
                    "                                    <td style=\"height:40px;\">&nbsp;</td>\n" +
                    "                                </tr>\n" +
                    "                            </table>\n" +
                    "                        </td>\n" +
                    "                    <tr>\n" +
                    "                        <td style=\"height:20px;\">&nbsp;</td>\n" +
                    "                    </tr>\n" +
                    "                    <tr>\n" +
                    "                        <td style=\"text-align:center;\">\n" +
                    "                            <p style=\"font-size:14px; color:rgba(69, 80, 86, 0.7411764705882353); line-height:18px; margin:0 0 0;\">&copy; <strong>www.artyVenci.com</strong></p>\n" +
                    "                        </td>\n" +
                    "                    </tr>\n" +
                    "                    <tr>\n" +
                    "                        <td style=\"height:80px;\">&nbsp;</td>\n" +
                    "                    </tr>\n" +
                    "                </table>\n" +
                    "            </td>\n" +
                    "        </tr>\n" +
                    "    </table>\n" +
                    "    <!--/100% body table-->\n" +
                    "</body>\n";
            message.setContent(htmlCode,"text/html");
            return  message;
        }catch (Exception ex){
Logger.getLogger(JavaMailutil.class.getName()).log(Level.SEVERE,null,ex)   ;     }
        return null;

    }




    public static  void SendMail2(String recepient,String Pass,String username) throws Exception{
        System.out.println("Preparing to Send email ");
        Properties properties = new Properties();

        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","smtp.googlemail.com");
        properties.put("mail.smtp.port","587");

        String myAccountEmail = "artyvenci@gmail.com";
        String pasword = "eziw vkjm nkks ment";


        Session session = Session.getInstance(properties, new Authenticator()
        {
            @Override
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(myAccountEmail,pasword);
            }

        });

        Message message = prepareMessage2(session,myAccountEmail,recepient,Pass,username);
        Transport.send(message);
        System.out.println("Message sends succefully");
    }
    private  static Message prepareMessage2(Session session , String myAccountEmail , String recepient , String Pass,String username){
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO,new InternetAddress(recepient));
            message.setSubject("Récupération de compte ArtyVenci");
            String htmlCode ="<head>\n" +
                    "    <meta content=\"text/html; charset=utf-8\" http-equiv=\"Content-Type\" />\n" +

                    "    <meta name=\"description\" content=\"Reset Password Email Template.\">\n" +
                    "    <style type=\"text/css\">\n" +
                    "        a:hover {text-decoration: underline !important;}\n" +
                    "    </style>\n" +
                    "</head>\n" +
                    "\n" +
                    "<body marginheight=\"0\" topmargin=\"0\" marginwidth=\"0\" style=\"margin: 0px; background-color: #f2f3f8;\" leftmargin=\"0\">\n" +
                    "    <!--100% body table-->\n" +
                    "    <table cellspacing=\"0\" border=\"0\" cellpadding=\"0\" width=\"100%\" bgcolor=\"#f2f3f8\"\n" +
                    "        style=\"@import url(https://fonts.googleapis.com/css?family=Rubik:300,400,500,700|Open+Sans:300,400,600,700); font-family: 'Open Sans', sans-serif;\">\n" +
                    "        <tr>\n" +
                    "            <td>\n" +
                    "                <table style=\"background-color: #f2f3f8; max-width:670px;  margin:0 auto;\" width=\"100%\" border=\"0\"\n" +
                    "                    align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                    "                    <tr>\n" +
                    "                        <td style=\"height:80px;\">&nbsp;</td>\n" +
                    "                    </tr>\n" +
                    "                    <tr>\n" +
                    "                        <td style=\"text-align:center;\">\n" +
                    "                          <a href=\"https://rakeshmandal.com\" title=\"logo\" target=\"_blank\">\n" +
                    "                            <img width=\"150\" src=\"https://i.ibb.co/q1tznwp/422753690-204703712734911-8704086809119957789-n.png\" title=\"logo\" alt=\"logo\">\n" +
                    "                          </a>\n" +
                    "                        </td>\n" +
                    "                    </tr>\n" +
                    "                    <tr>\n" +
                    "                        <td style=\"height:20px;\">&nbsp;</td>\n" +
                    "                    </tr>\n" +
                    "                    <tr>\n" +
                    "                        <td>\n" +
                    "                            <table width=\"95%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\"\n" +
                    "                                style=\"max-width:670px;background:#fff; border-radius:3px; text-align:center;-webkit-box-shadow:0 6px 18px 0 rgba(0,0,0,.06);-moz-box-shadow:0 6px 18px 0 rgba(0,0,0,.06);box-shadow:0 6px 18px 0 rgba(0,0,0,.06);\">\n" +
                    "                                <tr>\n" +
                    "                                    <td style=\"height:40px;\">&nbsp;</td>\n" +
                    "                                </tr>\n" +
                    "                                <tr>\n" +
                    "                                    <td style=\"padding:0 35px;\">\n" +
                    "                                        <h1 style=\"color:#1e1e2d; font-weight:500; margin:0;font-size:32px;font-family:'Rubik',sans-serif;\">You have\n" +
                    "                                           Vous avez demandé de récupérer votre compte</h1>\n" +
                    "                                        <span\n" +
                    "                                            style=\"display:inline-block; vertical-align:middle; margin:29px 0 26px; border-bottom:1px solid #cecece; width:100px;\"></span>\n" +
                    "                                        <p style=\"color:#455056; font-size:15px;line-height:24px; margin:0;\">\n" +
                    "                                            Voici les informations de récupération de votre compte :\n\n" +
                    "                                             Nom d'utilisateur : " + username + "\n" +
                    "                                            Mot de passe : " + Pass + "\n\n" +
                    "Si vous n'avez pas demandé cette récupération ou si vous avez des préoccupations, veuillez contacter immédiatement notre équipe d'assistance.\n\n" +
                    "                                        </p>\n" +

                    "                                    </td>\n" +
                    "                                </tr>\n" +
                    "                                <tr>\n" +
                    "                                    <td style=\"height:40px;\">&nbsp;</td>\n" +
                    "                                </tr>\n" +
                    "                            </table>\n" +
                    "                        </td>\n" +
                    "                    <tr>\n" +
                    "                        <td style=\"height:20px;\">&nbsp;</td>\n" +
                    "                    </tr>\n" +
                    "                    <tr>\n" +
                    "                        <td style=\"text-align:center;\">\n" +
                    "                            <p style=\"font-size:14px; color:rgba(69, 80, 86, 0.7411764705882353); line-height:18px; margin:0 0 0;\">&copy; <strong>www.artyVenci.com</strong></p>\n" +
                    "                        </td>\n" +
                    "                    </tr>\n" +
                    "                    <tr>\n" +
                    "                        <td style=\"height:80px;\">&nbsp;</td>\n" +
                    "                    </tr>\n" +
                    "                </table>\n" +
                    "            </td>\n" +
                    "        </tr>\n" +
                    "    </table>\n" +
                    "    <!--/100% body table-->\n" +
                    "</body>\n";
            message.setContent(htmlCode,"text/html");
            return  message;

        }catch (Exception ex){
            Logger.getLogger(JavaMailutil.class.getName()).log(Level.SEVERE,null,ex)   ;     }
        return null;

    }
}
