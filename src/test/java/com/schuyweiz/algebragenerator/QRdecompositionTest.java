package com.schuyweiz.algebragenerator;

import com.schuyweiz.algebragenerator.tasks.QRdecomposition;
import org.junit.jupiter.api.Test;

import java.util.Random;

class QRdecompositionTest {

    @Test
    void testQMatrix() throws Exception {
        QRdecomposition qr =  new QRdecomposition(new Random().nextInt());
        System.out.println(qr.getAnswerContent());

    }

}