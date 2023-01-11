<?php

/* (1) 서버 DB에 연결.*/
    $con = mysqli_connect("localhost", "miraclestep", "비밀번호", "miraclestep"); // mysql 연결, IP, 사용자명, 비밀번호, 데이터베이스
    mysqli_query($con, 'SET NAMES utf8'); // 인코딩을 utf-8로 세팅. (한글 전송이 가능해짐.)

    $userID = isset($_POST["userID"]) ? $_POST["userID"] : "";
    $userInfoDate = isset($_POST["userInfoDate"]) ? $_POST["userInfoDate"] : "";

    $statement1 = mysqli_prepare($con, "SELECT UserID, UserName, UserDStep, UserImg FROM User WHERE UserID = ? ");
    mysqli_stmt_bind_param($statement1, "s", $userID);
    mysqli_stmt_execute($statement1);

    mysqli_stmt_store_result($statement1);
    mysqli_stmt_bind_result($statement1, $UserID, $UserName, $UserDStep, $UserImg);

    $statement2 = mysqli_prepare($con, "SELECT UserHeight, UserWeight, UserBirth FROM UserInfo WHERE UserID = ? ORDER BY UserInfoDate DESC");
    mysqli_stmt_bind_param($statement2, "s", $userID);
    mysqli_stmt_execute($statement2);

    mysqli_stmt_store_result($statement2);
    mysqli_stmt_bind_result($statement2, $Height, $Weight, $Birth);

    $statement3 = mysqli_prepare($con, "SELECT TotalUserDonation FROM Rank WHERE UserID = ?");
    mysqli_stmt_bind_param($statement3, "s", $userID);
    mysqli_stmt_execute($statement3);

    mysqli_stmt_store_result($statement3);
    mysqli_stmt_bind_result($statement3, $TotalUserDonation);

    $response = array();
    $response["success"] = false;

    while(mysqli_stmt_fetch($statement1) || mysqli_stmt_fetch($statement2) || mysqli_stmt_fetch($statement3)) {
        $response["success"] = true;
        $response["userID"] = $UserID;
        $response["userName"] = $UserName;
        $response["userDStep"] = $UserDStep;
        $response["userImg"] = $UserImg;

        $response["height"] = $Height;
        $response["weight"] = $Weight;
        $response["birth"] = $Birth;

        $response["totalUserDonation"] = $TotalUserDonation;
    }
    echo json_encode($response);
?>
