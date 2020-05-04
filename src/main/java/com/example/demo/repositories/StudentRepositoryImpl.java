package com.example.demo.repositories;

import com.example.demo.models.Student;
import com.example.demo.util.DatabaseConnectionManager;
import org.springframework.beans.factory.annotation.Value;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class StudentRepositoryImpl implements IStudentRepository {
    private Connection conn;
    @Value("db.user")
    private String username;
    @Value("db.password")
    private String password;
    @Value("db.url")
    private String url;


    public StudentRepositoryImpl(){
        this.conn = DatabaseConnectionManager.getDatabaseConnection();
    }

    @Override
    public boolean create(Student student){
        try {
            PreparedStatement prep = conn.prepareStatement("INSERT INTO Student VALUES (DEFAULT, ?, ?, ?, ?, ?)");
            prep.setString(1, student.firstName);
            prep.setString(2, student.lastName);
            prep.setDate(3, Date.valueOf(student.enrollmentDate));
            prep.setString(4, student.cpr);
            prep.setString(5, student.profilePic);
            prep.executeUpdate();
        }catch(SQLException sql){
            sql.printStackTrace();
        }finally {
            try { conn.close(); } catch (SQLException sql) {sql.printStackTrace();}
            return true;
        }
    }

    @Override
    public Student read(int id) {
        Student studentToReturn = new Student();
        try {
            PreparedStatement getSingleStudent = conn.prepareStatement("SELECT * FROM student WHERE id=?");
            getSingleStudent.setInt(1,id);
            ResultSet rs = getSingleStudent.executeQuery();
            while(rs.next()){
                studentToReturn = new Student();
                studentToReturn.setId(rs.getInt(1));
                studentToReturn.setFirstName(rs.getString(2));
                studentToReturn.setLastName(rs.getString(3));
                studentToReturn.setEnrollmentDate(rs.getDate(4).toLocalDate());
                studentToReturn.setCpr(rs.getString(5));
            }
        }
        catch(SQLException s){
            s.printStackTrace();
        }finally {
            try { conn.close(); } catch (SQLException sql) {sql.printStackTrace();}
            return studentToReturn;
        }
    }

    @Override
    public List<Student> readAll() {
        List<Student> allStudents = new ArrayList<Student>();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM student");
            ResultSet rs = ps.executeQuery();
            if(rs != null) {
                while (rs.next()) {
                    Student tempStudent = new Student();
                    tempStudent.setId(rs.getInt(1));
                    tempStudent.setFirstName(rs.getString(2));
                    tempStudent.setLastName(rs.getString(3));
                    tempStudent.setEnrollmentDate(rs.getDate(4).toLocalDate());
                    tempStudent.setCpr(rs.getString(5));
                    allStudents.add(tempStudent);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try { conn.close(); } catch (SQLException sql) {sql.printStackTrace();}
            return allStudents;
        }
    }

    @Override
    public boolean update(Student student) {
        try {
            PreparedStatement prep = conn.prepareStatement("UPDATE Student SET  " +
                    "firstName = ?, " +
                    "lastName = ?," +
                    "enrollmentDate = ?," +
                    "cpr = ?," +
                    "picture = ? " +
                    "WHERE id=?");
            prep.setString(1, student.firstName);
            prep.setString(2, student.lastName);
            prep.setDate(3, Date.valueOf(student.enrollmentDate));
            prep.setString(4, student.cpr);
            prep.setString(5, student.profilePic);
            prep.setInt(6, student.id);
            System.out.println("in update: " + student);
            prep.executeUpdate();
            return true;
        }catch(SQLException sql){
            sql.printStackTrace();
        }finally {
            try { conn.close(); } catch (SQLException sql) {sql.printStackTrace();}
            return true;
        }
    }

    @Override
    public boolean delete(int id) {
        try {
            PreparedStatement prep = conn.prepareStatement("DELETE FROM student WHERE id=?");
            prep.setInt(1, id);
            prep.executeUpdate();
        }catch(SQLException sql){
            sql.printStackTrace();
        }finally {
            try { conn.close(); } catch (SQLException sql) {sql.printStackTrace();}
            return true;
        }
    }
}
