package com.ssafy.cafe.model.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class User {
    private String id;
    private String name;
    private String pass;
    private String email;
    private String role;
    private Integer stamps;
    private String provider;

    @Builder.Default
    private List<Stamp> stampList = new ArrayList<>();
    public List<String> getRoleList(){
        if(this.role.length()>0){
            return Arrays.asList(this.role.split(","));
        }
        return new ArrayList<>();
    }
    public User(String id, String name, String pass, Integer stamps) {
        this.id = id;
        this.name = name;
        this.pass = pass;
        this.stamps = stamps;
    }
    public User(){

    }


	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", pass=" + pass + ", stamps=" + stamps + ", stampList="
				+ stampList + "]";
	}

    
    
}