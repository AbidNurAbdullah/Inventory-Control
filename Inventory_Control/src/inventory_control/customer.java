/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inventory_control;

/**
 *
 * @author User
 */

public class customer {
    
    private String name;
    private String address;
    private String phone;
    private String email;

    public customer( String name, String address, String phone, String email) {

        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }


    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }

    public void setName(String name) { this.name = name; }
    public void setAddress(String address) { this.address = address; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setEmail(String email) { this.email = email; }
}
    

