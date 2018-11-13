/******************************************************************************
 *                       COPYRIGHT 2002 - 2012 BY DELL INC.
 *                          ALL RIGHTS RESERVED
 *
 * THIS DOCUMENT OR ANY PART OF THIS DOCUMENT MAY NOT BE REPRODUCED WITHOUT
 * WRITTEN PERMISSION FROM DELL INC.
 *****************************************************************************/

package com.dell.petshow.common;

import java.time.LocalDate;

/**
 * @author map6
 */
public class TimeTest {

    /**
     * @args command line arguments
     */
    public static void main(String[] args) {

        //2018-10-23 00:00:00 - 2018-10-29 23:59:59

        LocalDate localDate = LocalDate.now();
        System.out.println(localDate.toString());



    }
}
