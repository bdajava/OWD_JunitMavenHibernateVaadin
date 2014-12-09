
import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author robertsp
 */
public class Person {
private String firstname;
private String lastname;
private Date birthdate;
private long id;
public Person(long id){
    
}
    /**
     * @return the firstname
     */
    public String getFirstName() {
        return firstname;
    }

    /**
     * @param firstname the firstname to set
     */
    public void setFirstName(String firstname) {
        this.firstname = firstname;
    }

    /**
     * @return the lastname
     */
    public String getLastName() {
        return lastname;
    }

    /**
     * @param lastname the lastname to set
     */
    public void setLastName(String lastname) {
        this.lastname = lastname;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the bithdate
     */
    public Date getBirthdate() {
        return bithdate;
    }

    /**
     * @param bithdate the bithdate to set
     */
    public void setBirthdate(Date bithdate) {
        this.bithdate = bithdate;
    }
}
