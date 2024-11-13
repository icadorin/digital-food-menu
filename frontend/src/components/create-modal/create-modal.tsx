import { useEffect, useState } from "react";
import { useFoodDataMutate } from "../../hooks/useFoodDataMutate";
import { FoodData } from '../../interface/FoodData';

import "./modal.css";

interface InputProps {
    label: string,
    value: string | number,
    updateValue(value: any): void
}

interface ModalProps {
    closeModal(): void
}

const Input = ({ label, value, updateValue }: InputProps) => {
    return (
        <>
            <label>{label}</label>
            <input value={value} onChange={event => updateValue(event.target.value)}></input>
        </>
    );
}

export function CreateModal({ closeModal }: ModalProps) {
    const [title, setTitle] = useState("");
    const [price, setPrice] = useState(0);
    const [image, setImage] = useState("");
    const { mutate, isSuccess, isPending } = useFoodDataMutate();
 
    const submit = () => {
        if (!title || !price || !image) {
            alert("Por favor, preencha todos os campos.");
            return;
        }

        const foodData: FoodData = {
            title,
            price,
            image
        };

        mutate(foodData);
    }

    useEffect(() => {
        if (!isSuccess && !isPending) return;
        
        if (isSuccess) {
            closeModal();
        }
    }, [isSuccess, isPending, closeModal]);

    const handleOverlayClick = (event: React.MouseEvent<HTMLDivElement>) => {
        if ((event.target as Element).classList.contains('modal-overlay')) {
            closeModal();
        }
    };

    const isFormValid = title && price && image;

    return (
        <div className="modal-overlay" onClick={handleOverlayClick}>
            <div className="modal-body">
                <h2>Cadastre um novo item no cardápio</h2>
                <form className="input-container">
                    <Input label="title" value={title} updateValue={setTitle}/>
                    <Input label="price" value={price} updateValue={setPrice}/>
                    <Input label="image" value={image} updateValue={setImage}/>
                </form>
                <button 
                    onClick={submit} 
                    className="btn-secondary" 
                    disabled={!isFormValid}
                >
                    {isPending ? 'postando...' : 'postar'}
                </button>
            </div>
        </div>
    );
}
