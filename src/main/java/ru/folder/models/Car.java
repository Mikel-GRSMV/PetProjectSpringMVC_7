package ru.folder.models;

import lombok.*;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "owner")

public class Car {
    private Long id;
    private String model;
    private User owner;
}
