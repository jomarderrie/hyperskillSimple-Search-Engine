package search.model;


public class Person {
    private String userName;
    private String surName;
    private String email;



    public Person(String userName, String surName, String email) {
        this.userName = userName;
        this.surName = surName;
        this.email = email;
    }


    public Person(String userName, String surName) {
        this.userName = userName;
        this.surName = surName;
    }

    public Person(Person person) {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        if (getEmail()!=null){
            return  userName.trim() + " " + surName.trim()  + " " +email.trim();
        }else{
            return  userName.trim() + " " + surName.trim();
        }
    }


}
