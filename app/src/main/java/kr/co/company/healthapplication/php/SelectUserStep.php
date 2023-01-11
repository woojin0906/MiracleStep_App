<?php

/* (1) 서버 DB에 연결.*/
    $con = mysqli_connect("localhost", "miraclestep", "비밀번호", "miraclestep"); // mysql 연결, IP, 사용자명, 비밀번호, 데이터베이스
    mysqli_query($con, 'SET NAMES utf8'); // 인코딩을 utf-8로 세팅. (한글 전송이 가능해짐.)

 /* (2) id 가져옴. */
    $userID = isset($_POST["userID"]) ? $_POST["userID"] : "";

    /* (3) 현재 DB에 저장된 값을 검색함. */
    $statement = mysqli_prepare($con, "SELECT UserDStep FROM User WHERE UserID = ? ");
        mysqli_stmt_bind_param($statement, "s", $userID);
    mysqli_stmt_execute($statement);

  // /* (4) DB안에 해당 카테고리가 일치하는 Donation정보 가져오기 */
    mysqli_stmt_store_result($statement);
        mysqli_stmt_bind_result($statement, $DBUserStep);

    $response = array();
        $response["success"] = false;

        while(mysqli_stmt_fetch($statement)) {
             $response["success"] = true;
            $response["DBUserStep"] = $DBUserStep;

        }
        echo json_encode($response);
?>
