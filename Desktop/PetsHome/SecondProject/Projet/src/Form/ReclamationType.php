<?php

namespace App\Form;

use App\Entity\Reclamation;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Validator\Constraints\NotBlank;
use Symfony\Component\Validator\Constraints\Length;



class ReclamationType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
        ->add('name', TextType::class, [
            'constraints' => [
                new NotBlank([
                    'message' => 'Le nom est requis'
                ]),
                new Length([
                    'min' => 5,
                    'minMessage' => 'Le nom doit contenir au moins {{ limit }} caractères'
                ]),
            ],
        ])

            ->add('comment')
            ->add('service' , ChoiceType::class, [
                'choices' => [
                    'Service 1' => 'service_1',
                    'Service 2' => 'service_2',
                    'Service 3' => 'service_3',
                    // Ajoutez autant d'options que nécessaire
                ],
                'placeholder' => 'Sélectionnez un service', // Optionnel : un libellé vide initial
                'constraints' => [
                    new NotBlank(),
                ],
            ]);
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Reclamation::class,
        ]);
    }
}
