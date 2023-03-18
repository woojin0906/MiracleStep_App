<?php
 /*
      InsertDonation.php : 걸음 수를 기부하는 사용자 php (POST 형식으로 MySQL로부터 데이터를 받아옴.)
      con : mysql 연결을 시도하는 변수.
      "$변수" 로 변수 선언
    */

/* (1) 서버 DB에 연결.*/
    $con = mysqli_connect("localhost", "miraclestep01", "비밀번호", "miraclestep01");
    mysqli_query($con, 'SET NAMES utf8');   /* 인코딩을 utf-8로 세팅. (한글 전송이 가능해짐.) */

/* (2) DB에 저장할 객체 선언. */
    $userID = isset($_POST["userID"]) ? $_POST["userID"] : "";

    // date타입을 php는 String으로 받아들이기 때문에 형변환
    $ddate = isset($_POST["ddate"]) ? $_POST["ddate"] : "";
    $ddate=str_replace(".","-",$ddate);
    $ddate=str_replace("/","-",$ddate);
    $ddate = date('Ymd', strtotime($ddate));

    $dNum = isset($_POST["dNum"]) ? $_POST["dNum"] : "";
    $userStep = isset($_POST["userStep"]) ? $_POST["userStep"] : "";

/* (3) DB안에 insert하는 문장. (UserDonation) */
    $statement = mysqli_prepare($con, "INSERT INTO DonationHistory (donationIndex, userId, donationDate, donationStep) VALUES (?,?,?,?)");
    mysqli_stmt_bind_param($statement, "issi", $dNum, $userID, $ddate, $userStep);
    mysqli_stmt_execute($statement);

/* (4) 성공 여부 전송. */
    $response = array();
    $response["success"] = true;

/* (5) 실행 결과 전송. */
    echo json_encode($response);
?>
