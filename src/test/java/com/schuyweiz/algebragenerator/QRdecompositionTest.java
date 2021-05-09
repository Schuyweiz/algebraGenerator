package com.schuyweiz.algebragenerator;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class QRdecompositionTest {

    @Test
    void testQMatrix() throws Exception {
        QRdecomposition qr =  new QRdecomposition(new Random().nextInt());
        System.out.println(qr.getAnswerContent());

    }

}