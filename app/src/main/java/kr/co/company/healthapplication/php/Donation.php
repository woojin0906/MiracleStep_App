<?php
    /*
      Donation.php : 캠페인을 처리하는 php (POST 형식으로 MySQL로부터 데이터를 받아옴.)
      con : mysql 연결을 시도하는 변수.
      "$변수" 로 변수 선언
    */

    /* (1) 서버 DB에 연결.*/
    $con = mysqli_connect("localhost", "miraclestep01", "비밀번호", "miraclestep01"); // mysql 연결, IP, 사용자명, 비밀번호, 데이터베이스
    mysqli_query($con, 'SET NAMES utf8'); // 인코딩을 utf-8로 세팅. (한글 전송이 가능해짐.)

    /* (2) 앱에서 선택한 카테고리 비교 */
            if($_POST["category"]=="people") {
                $cate = "people";
            } else if($_POST["category"]=="environment") {
                $cate = "environment";
            } else {
                $cate = "animal";
            }

    /* (3) 현재 DB에 저장된 값을 검색함. */
        $statement = mysqli_prepare($con, "SELECT campaignIndex, title, hostingGroup, nowDonation, content, lastDate, maxDonation FROM CampaignList WHERE category=?");
            mysqli_stmt_bind_param($statement, "s", $cate);
        mysqli_stmt_execute($statement);

    // /* (4) DB안에 해당 카테고리가 일치하는 Donation정보 가져오기 */
        mysqli_stmt_store_result($statement);
             mysqli_stmt_bind_result($statement, $DNum, $DTitleName, $DName, $DNowStep, $DContent, $DDate, $MaxStep);

           $revs = array();

         $allrevs = array();
        $response["success"] = false;

        while(mysqli_stmt_fetch($statement)) {

            $revs["success"] = true;
            $revs["titleName"] = $DTitleName;
            $revs["name"] = $DName;
            $revs["nowStep"] = $DNowStep;
            $revs["content"] = $DContent;
            $revs["date"] = $DDate;
            $revs["maxStep"] = $MaxStep;
            $revs["dNum"] = $DNum;

            $allrevs[] = $revs;
        }

    /* (5) 실행 결과 전송. */
    echo json_encode($allrevs);

?>
