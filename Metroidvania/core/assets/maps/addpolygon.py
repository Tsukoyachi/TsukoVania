
def tiled_topolygon(file:str):
    """
    Adds polygons to a tiled map.
    """
    with open(file, 'r') as f:
        l=f.readlines()
        i=0
        while "</layer>" not in l[i]:
            i+=1
        l_final=l[:i+1]
        i=0
        while "<data" not in l[i]: i+=1
        j=i
        while "</data>" not in l[j]: j+=1
        l_lines=l[i+1:j]
        for i in range(len(l_lines)):
            l_lines[i]=l_lines[i].replace("\n","").split(",")
            l_lines[i]=[int(x) for x in l_lines[i] if x!=""]
    l_final.append("""<objectgroup id="2" name="objects">\n""")
    id=20 #je commence à 20, valeur arbitraire
    scale=32
    for i in range(len(l_lines)):
        for j in range(len(l_lines[i])):
            if l_lines[i][j] not in [0,37]:    # 37: décoration pas en polygone si jamais tu as besoin
                l_final.append(f"""    <object id="{id}" x="{scale*j}" y="{scale*i}">\n""")
                l_final.append(f"""      <polygon points="{scale*j},{scale*i} {scale*(j+2)},{scale*i} {scale*(j+2)},{scale*(i+2)} {scale*j},{scale*(i+2)}" />\n""")
                l_final.append(f"""    </object>\n""")
                id+=1
    l_final.append("""  </objectgroup>\n""")
    l_final.append("""</map>\n""")
    print(l_final)
    with open("test.tmx", 'w') as f:
        f.writelines(l_final)



if __name__ == "__main__":
    tiled_topolygon("map0ancien.tmx")

