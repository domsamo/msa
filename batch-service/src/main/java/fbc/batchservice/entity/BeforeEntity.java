package fbc.batchservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

/**
 * ===========================================================
 *
 * @author : jglee
 * @packageName : fbc.batchservice.entity
 * @fileName : BeforeEntity
 * @date : 24. 12. 4.
 * @description :
 * ===========================================================
 */
@Entity
@Getter
@Setter
public class BeforeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
}

/*
INSERT INTO BeforeEntity (username) VALUES
('Alice Johnson'),
('Bob Smith'),
('Charlie Brown'),
('Diana Prince'),
('Edward Davis'),
('Fiona White'),
('George Harris'),
('Hannah Clark'),
('Ian Walker'),
('Jessica Lee'),
('Kevin Robinson'),
('Laura King'),
('Michael Scott'),
('Nina Evans'),
('Oscar Wright'),
('Pamela Adams'),
('Quincy Green'),
('Rachel Baker'),
('Sam Harris'),
('Tina Nelson'),
('Ursula Moore'),
('Victor Young'),
('Wendy Thompson'),
('Xander Garcia'),
('Yara Martinez'),
('Zachary Lewis'),
('Amelia Jones'),
('Benjamin Wilson'),
('Clara Taylor'),
('David Anderson'),
('Emma Martinez'),
('Frank Hernandez'),
('Grace Robinson'),
('Henry Walker'),
('Ivy Young'),
('Jack Scott'),
('Katherine Lee'),
('Liam Brown'),
('Mia Harris'),
('Noah King'),
('Olivia Adams'),
('Paul Nelson'),
('Quinn Green'),
('Riley White'),
('Sophia Davis'),
('Thomas Clark'),
('Uma Evans'),
('Vera Robinson'),
('William Wright'),
('Xena Baker'),
('Yusuf Harris'),
('Zoe Martinez'),
('Aaron Scott'),
('Bella Young'),
('Carlos Robinson'),
('Daisy Thompson'),
('Ethan Moore'),
('Faith Lewis'),
('Gina Clark'),
('Hank Green'),
('Iris Martinez'),
('James Taylor'),
('Kelly Adams'),
('Lucas King'),
('Maggie White'),
('Nathan Wilson'),
('Opal Harris'),
('Peter Scott'),
('Queenie Davis'),
('Ryan Baker'),
('Sandra Green'),
('Travis Johnson'),
('Ulysses Clark'),
('Vivian Martinez'),
('Walter White'),
('Xander Robinson'),
('Yvette King'),
('Zach Smith'),
('Allison Davis'),
('Bradley Moore'),
('Chloe Harris'),
('Daniel Green'),
('Emily Taylor'),
('Freddie Brown'),
('Gabriella Wilson'),
('Henry Scott'),
('Isabella White'),
('Jake Adams'),
('Kaitlyn Robinson'),
('Leo Clark'),
('Madison King'),
('Nina Brown'),
('Owen Martinez'),
('Peyton Harris'),
('Quinton Moore'),
('Rebecca White'),
('Steve Wilson'),
('Tara Scott'),
('Ursula Green'),
('Victor Harris'),
('Wendy Adams'),
('Xander King'),
('Yvonne Davis'),
('Zachary White');
 */
