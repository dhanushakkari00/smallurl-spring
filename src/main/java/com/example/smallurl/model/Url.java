package com.example.smallurl.model;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "urls")
public class Url {

}
