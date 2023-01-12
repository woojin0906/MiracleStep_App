<?php
 /*
      UpdateUserStep.php : 사용자의 기부 가능 걸음 수를 처리하는 php (POST 형식으로 MySQL로부터 데이터를 받아옴.)
      con : mysql 연결을 시도하는 변수.
      "$변수" 로 변수 선언
    */

/* (1) 서버 DB에 연결.*/
    $con = mysqli_connect("localhost", "miraclestep", "비밀번호", "miraclestep");
    mysqli_query($con, 'SET NAMES utf8');   /* 인코딩을 utf-8로 세팅. (한글 전송이 가능해짐.) */

/* (2) DB에 저장할 객체 선언. */
    $userID = isset($_POST["userID"]) ? $_POST["userID"] : "";

    $updateUserStep = isset($_POST["updateUserStep"]) ? $_POST["updateUserStep"] : "";

/* (3) DB안에 insert하는 문장. (User) */
    $statement = mysqli_prepare($con, "UPDATE User SET UserDStep = ? Where UserID = ?");
    mysqli_stmt_bind_param($statement, "is", $updateUserStep, $userID);
    mysqli_stmt_execute($statement);

/* (4) 성공 여부 전송. */
    $response = array();
    $response["success"] = true;

    echo json_encode($response);
?>
