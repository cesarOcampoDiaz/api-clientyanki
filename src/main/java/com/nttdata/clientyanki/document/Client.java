package com.nttdata.clientyanki.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection="client_yanki")
public class Client {

    private Integer id;
    private String firstName;
    private String lastName;
    private String direction;
    @NotNull
    private String phone;
    @NotNull
    private String imei;
    @Email
    private String email;
}
