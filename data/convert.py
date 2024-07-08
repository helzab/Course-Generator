import openpyxl

def read_xlsx_and_write_to_txt(xlsx_path, txt_path):
    workbook = openpyxl.load_workbook(xlsx_path)
    sheet = workbook.active

    with open(txt_path, 'w') as txt_file:
        for row in sheet.iter_rows(values_only=True):
            row_string = ';'.join([str(cell) if cell is not None else '' for cell in row])
            txt_file.write(row_string + '\n')

xlsx_path = 'excel_courses.xlsx'
txt_path = 'courses.txt'

read_xlsx_and_write_to_txt(xlsx_path, txt_path)
