<?php

/* (1) 서버 DB에 연결.*/
    $con = mysqli_connect("localhost", "miraclestep", "비밀번호", "miraclestep"); // mysql 연결, IP, 사용자명, 비밀번호, 데이터베이스
    mysqli_query($con, 'SET NAMES utf8'); // 인코딩을 utf-8로 세팅. (한글 전송이 가능해짐.)

    $userID = isset($_POST["userID"]) ? $_POST["userID"] : "test";

    $statement = mysqli_prepare($con, "SELECT Donation.DGroup, Donation.DName , UserDonation.UDDate, UserDonation.UserDStep FROM Donation LEFT JOIN UserDonation ON Donation.DNum = UserDonation.DNum WHERE UserDonation.UserID = ? ORDER BY UserDonation.UDDate DESC");
    mysqli_stmt_bind_param($statement, "s", $userID);
    mysqli_stmt_execute($statement);

    //mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $DonationGroup, $DonationName, $UserDonationDate, $UserDonationStep);

    /*$statement2 = mysqli_prepare($con, "SELECT DGroup, DName FROM Donation WHERE DNum = ".$DonationNumber);
    //mysqli_stmt_bind_param($statement2, "i", $DonationNumber);
    mysqli_stmt_execute($statement2);

    mysqli_stmt_store_result($statement2);
    mysqli_stmt_bind_result($statement2, $DonationGroup, $DonationName);*/

    $response = array();
    $allResponse = array();
    $response["success"] = false;

     while(mysqli_stmt_fetch($statement)){

        $response["success"] = true;
        $response["donationDate"] = $UserDonationDate;
        $response["donationStep"] = $UserDonationStep;
        $response["titleName"] = $DonationName;
        $response["groupName"] = $DonationGroup;

        $allResponse[] = $response;
    }
    echo json_encode($allResponse);
?>
