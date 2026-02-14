def writeTextToFile(a):
    file_name = "output1.txt"
    STATICKY_TEXT = "This is my static text which must be added to file. It is very long text and I do not know what they want to do with this terrible text. "
    combinovany_text = STATICKY_TEXT + str(a)
    with open (file_name,"w") as out:
        out.write(combinovany_text)
    return file_name
if __name__ == "__main__":
    a = input("zadejte svuj text: ")
    file_name = writeTextToFile(a)
    print(f"dodatecny text byl zapsan do: {file_name}")

