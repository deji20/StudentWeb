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
    public int create(Student student){
        try {
            //create prepared CREATE statement
            PreparedStatement prep = conn.prepareStatement("INSERT INTO Student VALUES (DEFAULT, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            prep.setString(1, student.firstName);
            prep.setString(2, student.lastName);
            prep.setDate(3, Date.valueOf(student.enrollmentDate));
            prep.setString(4, student.cpr);
            prep.setString(5, student.profilePic);
            prep.executeUpdate();

            //generated keys are the values created in the sql database when creating a new student
            //in this case we use it to get and return the id of the newly created student
            ResultSet rs = prep.getGeneratedKeys();
            rs.next();
            return rs.getInt(1);
        }catch(SQLException sql){
            sql.printStackTrace();
        }
        return 0;
    }

    @Override
    public Student read(int id) {
        Student studentToReturn = new Student();
        try {
            //create prepared SELECT statement
            PreparedStatement getSingleStudent = conn.prepareStatement("SELECT * FROM student WHERE id=?");
            getSingleStudent.setInt(1,id);
            ResultSet rs = getSingleStudent.executeQuery();

            //inputs values into a student which is then returned
            while(rs.next()){
                studentToReturn = new Student();
                studentToReturn.setId(rs.getInt(1));
                studentToReturn.setFirstName(rs.getString(2));
                studentToReturn.setLastName(rs.getString(3));
                studentToReturn.setEnrollmentDate(rs.getDate(4).toLocalDate());
                studentToReturn.setCpr(rs.getString(5));
                studentToReturn.setProfilePic(rs.getString(6));
            }
        }
        catch(SQLException s){
            s.printStackTrace();
        }
        return studentToReturn;
    }

    @Override
    public List<Student> readAll() {
        List<Student> allStudents = new ArrayList<Student>();
        try {
            //create prepared SELECT statement which returns all students
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM student");
            ResultSet rs = ps.executeQuery();
            if(rs != null) {
                //if there are any students insert them into the Student model and add them to arraylist
                while (rs.next()) {
                    Student tempStudent = new Student();
                    tempStudent.setId(rs.getInt(1));
                    tempStudent.setFirstName(rs.getString(2));
                    tempStudent.setLastName(rs.getString(3));
                    tempStudent.setEnrollmentDate(rs.getDate(4).toLocalDate());
                    tempStudent.setCpr(rs.getString(5));
                    tempStudent.setProfilePic(rs.getString(6));
                    allStudents.add(tempStudent);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allStudents;
    }

    @Override
    public boolean update(Student student) {
        try {
            //create prepared UPDATE statement which updates every student attribute of the student with the given id
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
            prep.executeUpdate();
            return true;
        }catch(SQLException sql){
            sql.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        try {
            //create prepared DELETE statement, which deletes the student with the given id
            PreparedStatement prep = conn.prepareStatement("DELETE FROM student WHERE id=?");
            prep.setInt(1, id);
            prep.executeUpdate();
            return true;
        }catch(SQLException sql){
            sql.printStackTrace();
        }
        return false;
    }
}
