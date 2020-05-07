package com.example.demo.repositories;

import com.example.demo.models.Course;
import com.example.demo.util.DatabaseConnectionManager;
import org.springframework.beans.factory.annotation.Value;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseRepositoryImpl implements ICourseRepository{
    private Connection conn;
    @Value("db.user")
    private String username;
    @Value("db.password")
    private String password;
    @Value("db.url")
    private String url;

    public CourseRepositoryImpl(){
        this.conn = DatabaseConnectionManager.getDatabaseConnection();
    }

    @Override
    public int create(Course course) {
        try {
            //create prepared CREATE statement
            PreparedStatement prep = conn.prepareStatement("INSERT INTO course VALUES (DEFAULT, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            prep.setString(1, course.getName());
            prep.setDate(2, Date.valueOf(course.getStartDate()));
            prep.setInt(3, course.getEtcs());
            prep.setString(4, course.getDescription());
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
    public Course read(int id) {
        Course course = new Course();
        try {
            //create prepared SELECT statement
            PreparedStatement getSingleStudent = conn.prepareStatement("SELECT * FROM course WHERE id=?");
            getSingleStudent.setInt(1,id);
            ResultSet rs = getSingleStudent.executeQuery();

            //inputs values into a student which is then returned
            while(rs.next()){
                course.setId(rs.getInt(1));
                course.setName(rs.getString(2));
                course.setStartDate(rs.getDate(3).toLocalDate());
                course.setEtcs(rs.getInt(4));
                course.setDescription(rs.getString(5));
            }
        }
        catch(SQLException s){
            s.printStackTrace();
        }
        return course;
    }

    @Override
    public List<Course> readAll() {
        List<Course> allCourses = new ArrayList<Course>();
        try {
            //create prepared SELECT statement which returns all students
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM course");
            ResultSet rs = ps.executeQuery();
            if(rs != null) {
                //if there are any students insert them into the Student model and add them to arraylist
                while (rs.next()) {
                    Course course = new Course();
                    course.setId(rs.getInt(1));
                    course.setName(rs.getString(2));
                    course.setStartDate(rs.getDate(3).toLocalDate());
                    course.setEtcs(rs.getInt(4));
                    course.setDescription(rs.getString(5));
                    allCourses.add(course);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allCourses;
    }

    @Override
    public boolean update(Course course) {
        try {
            //create prepared UPDATE statement which updates every student attribute of the student with the given id
            PreparedStatement prep = conn.prepareStatement("UPDATE course SET  " +
                    "Name = ?, " +
                    "startDate = ?," +
                    "etcs = ?," +
                    "description = ?," +
                    "WHERE id=?");
            prep.setString(1, course.getName());
            prep.setDate(2, Date.valueOf(course.getStartDate()));
            prep.setInt(3, course.getEtcs());
            prep.setString(4, course.getDescription());
            prep.setInt(5, course.getId());
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
            PreparedStatement prep = conn.prepareStatement("DELETE FROM course WHERE id=?");
            prep.setInt(1, id);
            prep.executeUpdate();
            return true;
        }catch(SQLException sql){
            sql.printStackTrace();
        }
        return false;
    }
}
