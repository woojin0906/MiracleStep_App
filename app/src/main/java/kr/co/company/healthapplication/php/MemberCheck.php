<?php
  // 비밀번호 재설정화면에서 회원여부를 확인하는 php (2023-01-05)

  /* (1) 서버 DB에 연결.*/
  $con = mysqli_connect("localhost", "miraclestep", "비밀번호를 입력할 자리입니다.(깃허브 올릴 때에는 안적을께요~)", "miraclestep");
  mysqli_query($con, 'SET NAMES utf8');   /* 인코딩을 utf-8로 세팅. (한글 전송이 가능해짐.) */

  /* (2) DB에 저장할 객체 선언. */
  $userID = isset($_POST["userID"]) ? $_POST["userID"] : "";

  /* (3) 현재 DB에 저장된 아이디와 비밀번호를 검색함. */
  $statement = mysqli_prepare($con, "SELECT * FROM User WHERE UserID = ?");
  mysqli_stmt_bind_param($statement, "s", $userID);
  mysqli_stmt_execute($statement);

  /* (4) DB안에 해당 아이디와 비밀번호가 일치하면 User테아블 정보 다 가져오기. */
      mysqli_stmt_store_result($statement);
      mysqli_stmt_bind_result($statement, $userID);

      $response = array();
      $response["success"] = false;

      while(mysqli_stmt_fetch($statement)) {
          $response["success"] = true;
          $response["userID"] = $userID;
      }

  /* (5) 로그인 실행 결과 전송. */
    echo json_encode($userID);
      echo json_encode($response);
  ?>
