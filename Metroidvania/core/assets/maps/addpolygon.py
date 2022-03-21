"""A bit of code to add polygon colliders to a tiled map.
Use the tile id 37 to make tiles that are not colliders, or use a different decorators list."""

def tiled_topolygon(file_input:str,file_output:str=None,decorators:list[int]=None):
    """
    Adds polygons to a tiled map.
    """

    if decorators is None:
        decorators=[0,37]
    with open(file_input, 'r') as file:
        list_lines=file.readlines()
        lines_before=0
        while "</layer>" not in list_lines[lines_before]:
            lines_before+=1
        l_final=list_lines[:lines_before+1]
        lines_before=0
        while "<data" not in list_lines[lines_before]:
            lines_before+=1
        lines_after=lines_before
        while "</data>" not in list_lines[lines_after]:
            lines_after+=1
        l_lines=list_lines[lines_before+1:lines_after]
        for value in l_lines:
            value=value.replace("\n","").split(",")
            value=[int(x) for x in value if x!=""]
    l_final.append("""<objectgroup id="2" name="objects">\n""")
    id_polygon=20 # I start at 20, but tiled started at 17 idk why.
    #  it's a security just in case, maybe the id changes if you add other layers
    scale=32
    for index,value in enumerate(l_lines):
        for index_2,value_2 in enumerate(value):
            if value_2 not in decorators:
                l_final.append(f"""    <object id="{id_polygon}" x="{scale*index_2}" y="{
                    scale*index}">\n""")
                if l_lines[index][index_2+1] not in decorators:
                    l_final.append(f"""      <polygon points="{scale*index_2},{scale*index} {
                        scale*(index_2+4)},{scale*index} {scale*(index_2+4)},{scale*(index+2)} {
                            scale*index_2},{scale*(index+2)}" />\n""")
                elif l_lines[index+1][index_2] not in decorators:
                    l_final.append(f"""      <polygon points="{scale*index_2},{scale*index} {
                        scale*(index_2+2)},{scale*index} {scale*(index_2+2)},{scale*(index+4)} {
                            scale*index_2},{scale*(index+4)}" />\n""")
                else:
                    l_final.append(f"""      <polygon points="{scale*index_2},{scale*index} {
                        scale*(index_2+2)},{scale*index} {scale*(index_2+2)},{scale*(index+2)} {
                            scale*index_2},{scale*(index+2)}" />\n""")
                l_final.append("    </object>\n")
                id_polygon+=1
    l_final.append("""  </objectgroup>\n""")
    l_final.append("""</map>\n""")
    print(l_final)
    if file_output is None:
        file_output="output.tmx"
    with open(file_output, 'w') as file:
        file.writelines(l_final)

if __name__ == "__main__":
    tiled_topolygon("map0ancien.tmx","newmap.tmx",[0,37])
