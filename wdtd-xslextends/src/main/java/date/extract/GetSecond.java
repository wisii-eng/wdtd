/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package date.extract;



public class GetSecond
{
  public String returnSecond(String inputFormat, String inputDate, String spreOne, String spreTwo, String spreThree)
  {
    String strOut = "";
    int i=DateDef.TimeChoice.getTimeFormat(inputFormat).ordinal()+1;
    switch (i)
    {
    case 1:
    {
    	int size =5;
    	if(spreOne!=null)
    	{
    		size+=spreOne.length();
    	}
    	if(spreTwo!=null)
    	{
    		size+=spreTwo.length();
    	}
    	 strOut = "substring(" + inputDate + ","+size+", 2)";
    	 break;
    }
    case 2:
    case 5:
    case 6:
      if ("".equals(spreThree)) {
        strOut = "substring(" + inputDate + ", string-length(" + 
          inputDate + ")-1, 2)";

        break ;
      }
     
      strOut = "substring(" + inputDate + ", string-length(" + 
        inputDate + ")-2, 2)";
      break;
    case 3:
      if (("".equals(spreOne)) && ("".equals(spreTwo)) && 
        ("".equals(spreThree))) {
        strOut = "substring(" + inputDate + ",5)"; break ;
      }if ((!"".equals(spreOne)) && 
        (!"".equals(spreTwo)) && 
        ("".equals(spreThree))) {
        strOut = "substring(" + inputDate + ",7)"; break ;
      }if ((("".equals(spreOne)) && 
        (!"".equals(spreTwo)) && 
        ("".equals(spreThree))) || (
        (!"".equals(spreOne)) && 
        ("".equals(spreTwo)) && 
        ("".equals(spreThree)))) {
        strOut = "substring(" + inputDate + ",6)"; break ;
      }if ((!"".equals(spreOne)) && 
        (!"".equals(spreTwo)) && 
        (!"".equals(spreThree))) {
        strOut = "substring-before(substring(" + inputDate + ",7),'" + 
          spreThree + "')";

        break ;
      }
      if ((("".equals(spreOne)) && 
        (!"".equals(spreTwo)) && 
        (!"".equals(spreThree))) || (
        (!"".equals(spreOne)) && 
        ("".equals(spreTwo)) && 
        (!"".equals(spreThree)))) {
        strOut = "substring-before(substring(" + inputDate + ",6),'" + 
          spreThree + "')";

        break ;
      }
      if ((!"".equals(spreOne)) || (!"".equals(spreTwo)) || 
        ("".equals(spreThree))) break; strOut = "substring-before(substring(" + inputDate + ",5),'" + 
        spreThree + "')";
      break;
    case 4:
      if ((!"".equals(spreOne)) && (!"".equals(spreTwo)) && 
        ("".equals(spreThree))) {
        strOut = "substring-after(substring(" + inputDate + ",4),'" + 
          spreTwo + "')";

        break ;
      }
      if ((!"".equals(spreOne)) && 
        (!"".equals(spreTwo)) && 
        (!"".equals(spreThree))) {
        strOut = "substring-before(substring-after(substring(" + 
          inputDate + ",4),'" + spreTwo + "'),'" + spreThree + 
          "')";

        break;
      }

      if (("".equals(spreOne)) && 
        (!"".equals(spreTwo)) && 
        ("".equals(spreThree))) {
        strOut = "substring-after(" + inputDate + ",'" + spreTwo + "')"; break;
      }if ((!"".equals(spreOne)) || 
        ("".equals(spreTwo)) || 
        ("".equals(spreThree))) break; strOut = "substring-before(substring-after(" + inputDate + ",'" + 
        spreTwo + "'),'" + spreThree + "')";
      break;
    case 7:
      if ((!"".equals(spreOne)) && ("".equals(spreTwo)) && 
        ("".equals(spreThree))) {
        strOut = "substring(substring-after(" + inputDate + ",'" + 
          spreOne + "'),3)";

        break;
      }
      if ((!"".equals(spreOne)) && 
        (!"".equals(spreTwo)) && 
        ("".equals(spreThree))) {
        strOut = "substring-after(substring-after(" + inputDate + ",'" + 
          spreOne + "'),'" + spreTwo + "')";

        break;
      }
      if ((!"".equals(spreOne)) && 
        (!"".equals(spreTwo)) && 
        (!"".equals(spreThree))) {
        strOut = "substring-before(substring-after(substring-after(" + 
          inputDate + ",'" + spreOne + "'),'" + spreTwo + 
          "'),'" + spreThree + "')";

        break;
      }

      if ((!"".equals(spreOne)) && 
        ("".equals(spreTwo)) && 
        (!"".equals(spreThree))) {
        strOut = "substring(substring-before(substring-after(" + 
          inputDate + ",'" + spreOne + "'),'" + spreThree + 
          "'),3)";

        break;
      }

      if (("".equals(spreOne)) && 
        (!"".equals(spreTwo)) && 
        ("".equals(spreThree))) {
        strOut = "substring-after(" + inputDate + ",'" + spreTwo + "')"; break ;
      }if ((!"".equals(spreOne)) || 
        ("".equals(spreTwo)) || 
        ("".equals(spreThree))) break; strOut = "substring-before(substring-after(" + inputDate + ",'" + 
        spreTwo + "'),'" + spreThree + "')";
      break;
    case 8:
      if ((!"".equals(spreOne)) && (!"".equals(spreTwo)) && 
        ("".equals(spreThree))) {
        strOut = "substring-after(substring-after(" + inputDate + ",'" + 
          spreOne + "'),'" + spreTwo + "')";

        break;
      }
      if (("".equals(spreOne)) || 
        ("".equals(spreTwo)) || 
        ("".equals(spreThree))) break; strOut = "substring-before(substring-after(substring-after(" + 
        inputDate + ",'" + spreOne + "'),'" + spreTwo + 
        "'),'" + spreThree + "')";
      break;
    case 13:
    case 15:
    case 17:
    case 19:
      if ("".equals(spreTwo)) {
        strOut = "substring(" + inputDate + ", string-length(" + 
          inputDate + ")-1, 2)";

        break ;
      }

      strOut = "substring(" + inputDate + ", string-length(" + 
        inputDate + ")-2, 2)";
      break;
    case 14:
    case 18:
      if (("".equals(spreOne)) && ("".equals(spreTwo))) {
        strOut = "substring(" + inputDate + ",3)";
      } else if ((!"".equals(spreOne)) && 
        ("".equals(spreTwo))) {
        strOut = "substring(" + inputDate + ",4)";
      } else if ((!"".equals(spreOne)) && 
        (!"".equals(spreTwo))) {
        strOut = "substring-before(substring(" + inputDate + ",4),'" + 
          spreTwo + "')"; } else {
        if ((!"".equals(spreOne)) || 
          ("".equals(spreTwo))) break;
        strOut = "substring-before(substring(" + inputDate + ",3),'" + 
          spreTwo + "')";
      }case 16:
    case 20:
      if ((!"".equals(spreOne)) && ("".equals(spreTwo))) {
        strOut = "substring-after(" + inputDate + ",'" + spreOne + "')"; break ;
      }if (("".equals(spreOne)) || 
        ("".equals(spreTwo))) break ; strOut = "substring-before(substring-after(" + inputDate + ",'" + 
        spreOne + "'),'" + spreTwo + "')";
      break;
    case 25:
      strOut = "substring(" + inputDate + ",1, 2)";
      break;
    case 26:
      if ("".equals(spreOne)) {
        strOut = inputDate; break ;
      }
      strOut = "substring-before(" + inputDate + ",'" + spreOne + 
        "')";
      break;
    case 9:
    case 10:
    case 11:
    case 12:
    case 21:
    case 22:
    case 23:
    case 24: strOut = "''";
    } 

    return strOut;
  }

  public static void main(String[] args)
  {
    String strTime = "";
    GetSecond gt = new GetSecond();
    strTime = gt.returnSecond("hhmmss", "$inputDate", "", "s", "a");

    System.out.println("XSLT for Second: " + strTime);
  }
}