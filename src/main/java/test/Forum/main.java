package test.Forum;
import java.sql.*;

import services.ServicePostF;
import utils.MyDBF;
import entities.ForumEntity;
//import services.ServicePost;
import services.ServiceForumF;

public class main {
    public static void main(String[] args) {
        //Connect to Database
        MyDBF conn1 = MyDBF.getInstance();
        /*===================ForumEntities======================*/
        ForumEntity f1 = new ForumEntity("Abstract Art","Isnt it Beautiful?");
        ForumEntity f2 = new ForumEntity("Surrealisme is weird","Just for Testing");
        ForumEntity f3 = new ForumEntity(3, "Digital Art is Beautiful","I would like to know more about your opinions",5);
        /*===================ForumService=======================*/
        ServiceForumF forumService = new ServiceForumF();
//        //----------------Add Test----------------------
//        try {
//            forumService.ajouter(f1);
//            forumService.ajouter(f2);
//            forumService.ajouter(f3);
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        //----------------Show TEST----------------------
//        try {
//            System.out.println(forumService.afficher());
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        //----------------EDIT TEST----------------------
//        try {
//            forumService.modifier(f3);
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        //----------------Delete TEST----------------------
//        try {
//            forumService.supprimer(f3);
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        //----------------Show AFTER TEST----------------------
//        System.out.println("AFTER EDIT : ");
//        try {
//            System.out.println(forumService.afficher());
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

        /*================POST SERVICE====================*/
        ServicePostF postService = new ServicePostF();
        /*================POST Entities====================*/
//        PostEntity p1 = new PostEntity(1,1,"it is interesting");
//        PostEntity p2 = new PostEntity(2,1,"It really is");
//        PostEntity p3 = new PostEntity(4,2,1,"Indeed i do well ofc",5);
//        //----------------Add Test----------------------
//        try {
//            postService.ajouter(p1);
//            postService.ajouter(p2);
//            postService.ajouter(p3);
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        //----------------Show TEST----------------------
//        try {
//            System.out.println(postService.afficher());
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        //----------------EDIT TEST----------------------
//        try {
//            postService.modifier(p3);
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        //----------------Delete TEST----------------------
//        try {
//            postService.supprimer(p3);
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        //----------------Show AFTER TEST----------------------
//        System.out.println("AFTER EDIT : ");
//        try {
//            System.out.println(postService.afficher());
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

        /*================USER SERVICE====================*/
//        ServiceUserF SU = new ServiceUserF();
//        try {
//            System.out.println(SU.getbyid(2));
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }
}
