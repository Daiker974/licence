# coding: utf-8

from threading import Thread, Condition
from random import *
from tkinter import *
import time


class File():
    def __init__(self):
        self.file = []
        self.Cond = Condition()

    def deposer(self, prod):
        with self.Cond:
            b = randint(0, 100)
            prod.label["text"] = "Producteur : ajout de l'entier " + str(b)
            while (len(self.file) > 20):
                prod.label["text"] = "Producteur : file pleine."
                self.Cond.wait()
            self.file.append(b)
            fileLabel["text"] = "File : " + str(self.file)
            self.Cond.notifyAll()

    def retirer(self, consom):
        with self.Cond:
            while (len(self.file) == 0):
                consom.label["text"] = "Consommateur " + str(
                    consom.n) + " : file vide."
                self.Cond.wait()
            r = self.file[0]
            self.file.remove(self.file[0])
            consom.label["text"] = "Consommateurs " + str(consom.n) + " : retrait de " + str(r) + " effectué"
            fileLabel["text"] = "File : " + str(self.file)
            self.Cond.notifyAll()

class Producteurs(Thread):
    def __init__(self, temps, file):
        Thread.__init__(self)
        self.temps = int(temps)
        self.file = file
        self.label = Label(fenetre, text="")
        self.label.pack()
        self.daemon = True

    def run(self):
        while (1):
            self.file.deposer(self)
            time.sleep(self.temps)

class Consommateurs(Thread):
    def __init__(self, temps, file, n):
        Thread.__init__(self)
        self.temps = int(temps)
        self.file = file
        self.n = n
        self.label = Label(fenetre, text="")
        self.label.pack()
        self.daemon = True

    def run(self):
        while (1):
            self.file.retirer(self)
            time.sleep(self.temps)

def lancement():
    file = File()
    prod = Producteurs(1, file)
    cons1 = Consommateurs(3, file, 1)
    cons2 = Consommateurs(3, file, 2)

    boutonstart.pack_forget()
    boutonstop = Button(fenetre, text="Quitter", command=fermeture)
    boutonstop.pack()

    prod.start()
    cons1.start()
    cons2.start()

def fermeture():
    fenetre.destroy()

fenetre = Tk()
fenetre.title("File d'entiers")
fenetre.geometry("500x120")
fileLabel = Label(fenetre, text="File : <>")
fileLabel.pack()
boutonstart = Button(fenetre, text="Commencer", command=lancement)
boutonstart.pack()
fenetre.mainloop()