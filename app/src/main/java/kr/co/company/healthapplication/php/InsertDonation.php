<?php
/* (1) 서버 DB에 연결.*/
    $con = mysqli_connect("localhost", "miraclestep", "비밀번호", "miraclestep");
    mysqli_query($con, 'SET NAMES utf8');   /* 인코딩을 utf-8로 세팅. (한글 전송이 가능해짐.) */

/* (2) DB에 저장할 객체 선언. */
    $userID = isset($_POST["userID"]) ? $_POST["userID"] : "";

    // date타입을 php는 String으로 받아들이기 때문에 형변환이 필요해요... (2023-01-03 이수)
    $ddate = isset($_POST["ddate"]) ? $_POST["ddate"] : "";
    $ddate=str_replace(".","-",$ddate);
    $ddate=str_replace("/","-",$ddate);
    $ddate = date('Ymd', strtotime($ddate));

    $dNum = isset($_POST["dNum"]) ? $_POST["dNum"] : "";
    $userStep = isset($_POST["userStep"]) ? $_POST["userStep"] : "";

/* (3) DB안에 insert하는 문장. (User, UserInfo) */
    $statement = mysqli_prepare($con, "INSERT INTO UserDonation (DNum, UserID, UDDate, UserDStep) VALUES (?,?,?,?)");
    mysqli_stmt_bind_param($statement, "issi", $dNum, $userID, $ddate, $userStep);
    mysqli_stmt_execute($statement);

/* (4) 성공 여부 전송. */
    $response = array();
    $response["success"] = true;

    echo json_encode($response);
?>
