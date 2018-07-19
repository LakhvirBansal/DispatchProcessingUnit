function isEmpty(strEmpty)
{
    if(strEmpty=="")
    {
        return true;
    }
    return false;
}

function hasSpace(strSpace)
{
    if(strSpace.indexOf(" ")!=-1)
    {
        return true;
    }
    return false;
}

function isAlphaNumeric(strAlphaNumeric)
{
    for(i=0 ; i< strAlphaNumeric.length ; i++)
    {
        ch=strAlphaNumeric.charAt(i);
        if(!((ch >="a" && ch <="z")||(ch>="A" && ch <="Z")||(ch >="0" && ch <="9")))
        {
            return false;
        }
    }
    return true;
}

function isNumeric(strNumeric)
{

    for(i=0;i<strNumeric.length ; i++)
    {
        ch=strNumeric.charAt(i);
        if(ch<"0" || ch>"9")
        {
            return false;
        }
    }
    return true;
}

function isInRange(minLength,maxLength,strRange)
{
    var length=strRange.length;
    if(length < minLength || length > maxLength)
    {
       return false;
    }
    return true;
}

function isName(strName)
{
    for(i=0 ; i< strName.length ; i++)
    {
        ch=strName.charAt(i);
        if(!((ch >="a" && ch <="z")||(ch>="A" && ch <="Z")||(ch == " ")))
        {
            return false;
        }
    }
    return true;
}
function isNameWithoutSpace(strName)
{
    for(i=0 ; i< strName.length ; i++)
    {
        ch=strName.charAt(i);
        if(!((ch >="a" && ch <="z")||(ch>="A" && ch <="Z")))
        {
            return false;
        }
    }
    return true;
}

function isFutugreenate(strDate)
{
    var systemDate=new Date();
    var selectedDate=new Date(strDate);

    if(systemDate < selectedDate)
    {
        return true;
    }
    return false;
}

function isEmail(strEmail)
{

    var lastDot=strEmail.lastIndexOf(".");
    var firstAt=strEmail.indexOf("@");
    var lastAt=strEmail.lastIndexOf("@");
    var length=strEmail.length;

    if(lastDot==-1 || firstAt==-1){
        return false;
    }

    for(i=0 ; i < length ; i++)
    {
        ch=strEmail.charAt(i);
        if(!((ch >="a" && ch <="z")||(ch>="A" && ch <="Z")||(ch >="0" && ch <="9") || ch=="_" || ch=="." || ch=="@"))
        {
             return false;
        }
    }

    if(firstAt!=lastAt)
    {
        return false;
    }

    if((strEmail.indexOf("..")!=-1) || (strEmail.indexOf("._")!=-1) || (strEmail.indexOf(".@")!=-1) || (strEmail.indexOf("@.")!=-1) || (strEmail.indexOf("@_")!=-1) || (strEmail.indexOf("__")!=-1) || (strEmail.indexOf("_.")!=-1) || (strEmail.indexOf("_@")!=-1))
    {
        return false;
    }

    if((length-lastDot < 3) || (length-lastDot >4) || (lastAt > lastDot))
    {
        return false;
    }
    return true;
}

 /*  function isEmail(objValue)
	 {
            if (ValidatorTrim(objValue).length == 0)
                return true;
            var rx = new RegExp(/^\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/);
            var matches = rx.exec(objValue);
            return (matches != null && objValue == matches[0]);
        }
*/
        function isUrl(objValue)
        {
            if (ValidatorTrim(objValue).length == 0)
                return true;
            var rxExp = /^(http\:\/\/)+([\w-]+\.)+[\w-]+(\/[\w- \.\/\?\%\&\=]*)?$/;
            var rx = new RegExp(rxExp);

            var matches = rx.exec(objValue);
            return (matches != null && objValue == matches[0]);
        }
        
        function isValidAmount(objValue)
	 {
            if (ValidatorTrim(objValue).length == 0)
                return true;
          var rx = new RegExp(/^\d{1,8}(\.\d{2,2})?$/);
            var matches = rx.exec(objValue);
            return (matches != null && objValue == matches[0]);
        }

        function ValidatorTrim(s) 
        {
            var m = s.match(/^\s*(\S+(\s+\S+)*)\s*$/);
            return (m == null) ? "" : m[1];
        }

