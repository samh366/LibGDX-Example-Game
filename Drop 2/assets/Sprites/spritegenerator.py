from PIL import Image

# Splits an n x n sprite png into n^2 individual images

def splitter():
    # Assumes order of front, back, right, left
    names = ["front", "back", "right", "left"]
    name = input("filename: ")
    rows = int(input("rows: "))
    columns = int(input("columns: "))
    filename = input("name of animation: ")


    image = Image.open(name)
    width = image.width // columns
    height = image.height // columns

    for y in range(rows):
        for x in range(columns):
            im = image.crop((x*width, y*height, x*width + width, y*height + height))
            im.save(filename + "_" + names[y] + "_" + str(x) + ".png")
    
    print("Done!")





if __name__ == "__main__":
    while True:
        try:
            splitter()
        
        except Exception as e:
            print(e)
            print("Something went wrong, try again")