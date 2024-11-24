import {createContext} from 'react';
import { PostContextType } from '../types';

const postContext = createContext<PostContextType | null>(null);

export default postContext;
